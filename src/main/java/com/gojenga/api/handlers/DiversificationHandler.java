package com.gojenga.api.handlers;

import com.gojenga.api.models.SpdrSymbol;
import com.gojenga.api.repository.SpdrSectorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//for some reason the autowired repository keep scoming up null

@Component
public class DiversificationHandler {
    @Autowired
    @Qualifier("spdrSectorsRepository")
    private SpdrSectorsRepository spdrSectorsRepository;

    public String getMatchingSector(String sector) {
        String matchingSector = "";
        sector = sector.toLowerCase();
        if (sector.equals("xly")) {
            matchingSector = "xlp";
        }
        return matchingSector;
    }

    public String getRecommendedSymbol(String sector) {
        List<SpdrSymbol> sectorSymbols = spdrSectorsRepository.getAllBySector(sector);

        if (sectorSymbols.size() > 0) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, sectorSymbols.size());

            SpdrSymbol selectedSymbol = sectorSymbols.get(randomIndex);
            return selectedSymbol.getSymbol();
        } else {
            return "";
        }
    }

}
