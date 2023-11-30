package com.gojenga.api.repository;

import com.gojenga.api.models.Account;
import com.gojenga.api.models.Calculation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("calculationsRepository")
public interface CalculationsRepository extends CrudRepository<Calculation, Integer> {
//    Calculation findCalculationBySymbolAndStartDateAndEndDate(String symbol, Date startDate, Date endDate);

    @Query(value = "SELECT * FROM calculations WHERE Symbol = 'lulu' AND StartDate = '2022-10-01' AND EndDate = '2023-10-01'", nativeQuery = true)
    Calculation findBySymbolStartDateEndDate(@Param("value") String Symbol, Date StartDate, Date EndDate);

    Calculation findCalculationBySymbol(String Symbol);
    @Query("SELECT p.symbol FROM Calculation p")
    List<String> findAllNames();
}