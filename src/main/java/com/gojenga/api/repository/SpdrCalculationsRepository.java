package com.gojenga.api.repository;

import com.gojenga.api.models.Calculation;
import com.gojenga.api.models.SpdrCalculation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("spdrCalculationsRepository")

public interface SpdrCalculationsRepository extends CrudRepository<SpdrCalculation, Integer> {

    SpdrCalculation findSpdrCalculationByBaseSymbolAndCorrSymbol(String base_symbol, String corr_symbol);
}
