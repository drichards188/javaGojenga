package com.gojenga.api.controllers;

import com.gojenga.api.models.Account;
import com.gojenga.api.models.Calculation;
import com.gojenga.api.repository.AccountRepository;
import com.gojenga.api.repository.CalculationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController // This means that this class is a Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "api/risk") // This means URL's start with /demo (after Application path)
public class RiskController {
    @Autowired
    @Qualifier("calculationsRepository")
    private CalculationsRepository calculationsRepository;

    @GetMapping("")
    public ResponseEntity<Calculation> getSharpeRatio(@RequestParam String symbol) {
        try {
            String dateString = "2020-10-01"; // Example date in MM/dd/yyyy format
            String dateString2 = "2023-10-01";

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(dateString);
            Date endDate = dateFormat.parse(dateString2);

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
}
