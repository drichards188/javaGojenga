package com.gojenga.api.repository;

import com.gojenga.api.models.SpdrSymbol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("spdrSectorsRepository")
public interface SpdrSectorsRepository extends CrudRepository<SpdrSymbol, Integer> {
    SpdrSymbol findSpdrSectorsBySymbol(String Symbol);
    List<SpdrSymbol> getAllBySector(String sector);

    SpdrSymbol findNameBySymbol(String symbol);
}
