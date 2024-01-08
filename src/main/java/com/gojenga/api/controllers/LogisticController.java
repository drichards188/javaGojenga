package com.gojenga.api.controllers;

import com.gojenga.api.models.SpdrSymbol;
import com.gojenga.api.repository.SpdrSectorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController // This means that this class is a Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "api/logistic")
public class LogisticController {

    private final SpdrSectorsRepository spdrSectorsRepository;

    public LogisticController(SpdrSectorsRepository spdrSectorsRepository) {
        this.spdrSectorsRepository = spdrSectorsRepository;
    }

    @GetMapping("/lookup")
    public ResponseEntity<Map<String, String>> lookupName(@RequestParam String symbol) {
        try {
            if (symbol == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            SpdrSymbol result = spdrSectorsRepository.findNameBySymbol(symbol);

            String name = result.getName();

            Map<String, String> resp = Map.of("name", name);

            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
