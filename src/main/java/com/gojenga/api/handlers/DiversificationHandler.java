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

    public String getNameOfSymbol(String symbol) {
        SpdrSymbol symbolInfo = spdrSectorsRepository.findNameBySymbol(symbol);

        if (symbolInfo != null) {
            return symbolInfo.getName();
        } else {
            return "";
        }
    }

}
