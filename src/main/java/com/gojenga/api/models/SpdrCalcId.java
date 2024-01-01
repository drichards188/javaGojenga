package com.gojenga.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class SpdrCalcId implements Serializable {

    private String baseSymbol;  // corresponds to 'base_symbol' column
    private String corrSymbol;  // corresponds to 'corr_symbol' column

    // Default constructor
    public SpdrCalcId() {}

    // Constructor with fields
    public SpdrCalcId(String baseSymbol, String corrSymbol) {
        this.baseSymbol = baseSymbol;
        this.corrSymbol = corrSymbol;
    }

    // Getters, setters, hashCode, and equals methods
    // Implement equals and hashCode considering both fields
}
