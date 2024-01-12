package com.gojenga.api.controllers;

import com.gojenga.api.models.Calculation;
import com.gojenga.api.models.Portfolio;
import com.gojenga.api.repository.CalculationsRepository;
import com.gojenga.api.repository.ExchangeRepository;
import com.gojenga.api.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "api/portfolio")
public class PortfolioController {
    public PortfolioController(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Qualifier("portfolioRepository")
    private final PortfolioRepository portfolioRepository;

    @GetMapping("/{username}")
    public ResponseEntity<List<Portfolio>> getPortfolio(@PathVariable String username) {
        try {
            List<Portfolio> portfolios = (List<Portfolio>) portfolioRepository.findByUsername(username);

            if (portfolios != null) {
                return new ResponseEntity<>(portfolios, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<Boolean> updatePortfolio(@PathVariable String username, @RequestBody Map<String, String> payload) {
        try {
            Integer amount = Integer.parseInt(payload.get("amount"));
            String asset = payload.get("asset");

            Portfolio portfolio = new Portfolio(0,username, amount, asset);

            Portfolio foundPortfolio = portfolioRepository.findByUsernameAndAsset(username, asset);

            if (foundPortfolio == null) {
                portfolioRepository.save(portfolio);
            } else {
                foundPortfolio.setAmount(foundPortfolio.getAmount() + amount);
                portfolioRepository.save(foundPortfolio);
            }

            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
