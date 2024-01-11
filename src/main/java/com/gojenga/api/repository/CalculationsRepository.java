package com.gojenga.api.repository;

import com.gojenga.api.models.Calculation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("calculationsRepository")
public interface CalculationsRepository extends CrudRepository<Calculation, Integer> {
    Calculation findCalculationBySymbol(String symbol);
    @Query("SELECT p.symbol FROM Calculation p")
    List<String> findAllNames();

}