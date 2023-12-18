package com.gojenga.api.controllers;

import com.gojenga.api.handlers.DiversificationHandler;
import com.gojenga.api.models.Calculation;
import com.gojenga.api.models.SpdrSymbol;
import com.gojenga.api.repository.CalculationsRepository;
import com.gojenga.api.repository.ExchangeRepository;
import com.gojenga.api.repository.SpdrSectorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


    @GetMapping("")
    public ResponseEntity<List<String>> getDiverseRec(@RequestParam String symbol) {
        try {
            SpdrSymbol symbolInfo = spdrSectorsRepository.findSpdrSectorsBySymbol(symbol);

            if (symbolInfo != null) {
                DiversificationHandler diversificationHandler = new DiversificationHandler();
                String recommmendedSector = diversificationHandler.getMatchingSector(symbolInfo.getSector());

                List<SpdrSymbol> sectorSymbols = spdrSectorsRepository.getAllBySector(recommmendedSector);

                if (sectorSymbols.size() > 0) {
                    ArrayList<String> symbols = new ArrayList<>();
                    int i = 0;
                    while (i < 3) {
                        int randomIndex = ThreadLocalRandom.current().nextInt(0, sectorSymbols.size());

                        SpdrSymbol selectedSymbol = sectorSymbols.get(randomIndex);
                        symbols.add(selectedSymbol.getSymbol());
                        sectorSymbols.remove(randomIndex);
                        i++;
                    }
                    return new ResponseEntity<>(symbols, HttpStatus.OK);
                }

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

