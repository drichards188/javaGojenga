package com.gojenga.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exchange_symbols")
public class ExchangeSymbol {

    @Id
    private String symbol;
    @Column(nullable = false)
    private String exchange;

    public Boolean isEmpty() {
        if (this.symbol == null && this.exchange == null) {
            return true;
        } else {
            return false;
        }
    }
}