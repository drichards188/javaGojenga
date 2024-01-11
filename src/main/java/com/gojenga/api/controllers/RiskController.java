package com.gojenga.api.controllers;

import com.gojenga.api.models.Account;
import com.gojenga.api.models.Calculation;
import com.gojenga.api.models.ExchangeSymbol;
import com.gojenga.api.repository.AccountRepository;
import com.gojenga.api.repository.CalculationsRepository;
import com.gojenga.api.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "api/risk")
public class RiskController {
    public RiskController(CalculationsRepository calculationsRepository, ExchangeRepository exchangeRepository) {
        this.calculationsRepository = calculationsRepository;
        this.exchangeRepository = exchangeRepository;
    }

    @Qualifier("calculationsRepository")
    private final CalculationsRepository calculationsRepository;

    @Qualifier("exchangeRepository")
    private final ExchangeRepository exchangeRepository;

    @GetMapping("")
    public ResponseEntity<Calculation> getSharpeRatio(@RequestParam String symbol) {
        try {
            Calculation calculation = calculationsRepository.findCalculationBySymbol(symbol);

            if (calculation != null) {
                return new ResponseEntity<>(calculation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/symbols")
    public ResponseEntity<List<String>> getCalcSymbols() {
        try {
            List<String> symbols = calculationsRepository.findAllNames();

            if (symbols != null) {
                return new ResponseEntity<>(symbols, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/symbols/{symbol}")
    public ResponseEntity<HashMap<String, String>> getSymbolExchange(@PathVariable String symbol) {
        try {
            ExchangeSymbol exchange = exchangeRepository.findExchangeBySymbol(symbol);

            if (exchange != null) {
                System.out.println("got exchange");
                HashMap<String, String> resp = new HashMap<>();
                resp.put("exchange", exchange.getExchange());
                return new ResponseEntity<>(resp, HttpStatus.OK);
            } else {
                System.out.println("no exchange");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

