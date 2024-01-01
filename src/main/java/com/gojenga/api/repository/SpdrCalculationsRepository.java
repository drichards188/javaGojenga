package com.gojenga.api.repository;

import com.gojenga.api.models.Calculation;
import com.gojenga.api.models.SpdrCalculation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("spdrCalculationsRepository")

public interface SpdrCalculationsRepository extends CrudRepository<SpdrCalculation, Integer> {
    @Query("SELECT p FROM SpdrCalculation p")
    List<SpdrCalculation> getAllByBaseSymbol(@Param("baseSymbol") String baseSymbol);
}
