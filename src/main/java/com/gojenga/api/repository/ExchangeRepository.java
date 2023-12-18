package com.gojenga.api.repository;

import com.gojenga.api.models.Calculation;
import com.gojenga.api.models.ExchangeSymbol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("exchangeRepository")
public interface ExchangeRepository extends CrudRepository<ExchangeSymbol, Integer> {
    ExchangeSymbol findExchangeBySymbol(String Symbol);

}
