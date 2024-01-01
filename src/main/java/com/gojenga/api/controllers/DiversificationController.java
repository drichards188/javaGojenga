package com.gojenga.api.controllers;

import com.gojenga.api.handlers.DiversificationHandler;
import com.gojenga.api.models.Calculation;
import com.gojenga.api.models.SpdrCalculation;
import com.gojenga.api.models.SpdrSymbol;
import com.gojenga.api.repository.CalculationsRepository;
import com.gojenga.api.repository.ExchangeRepository;
import com.gojenga.api.repository.SpdrCalculationsRepository;
import com.gojenga.api.repository.SpdrSectorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController // This means that this class is a Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "api/diversification") // This means URL's start with /demo (after Application path)
public class DiversificationController {
    @Autowired
    @Qualifier("spdrSectorsRepository")
    private SpdrSectorsRepository spdrSectorsRepository;

    @Autowired
    @Qualifier("spdrCalculationsRepository")
    private SpdrCalculationsRepository spdrCalculationsRepository;

    @GetMapping("")
    public ResponseEntity<List<SpdrCalculation>> getDiverseRec(@RequestParam String symbol) {
        try {
            SpdrSymbol symbolInfo = spdrSectorsRepository.findSpdrSectorsBySymbol(symbol);

            if (symbolInfo != null) {

                List<SpdrCalculation> allCalculations = spdrCalculationsRepository.getAllByBaseSymbol(symbol);

                allCalculations.sort(Comparator.comparing(SpdrCalculation::getCorrelation));

                return new ResponseEntity<>(allCalculations, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }
         catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

