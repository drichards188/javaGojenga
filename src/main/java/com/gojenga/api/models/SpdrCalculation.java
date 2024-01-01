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
@Table(name = "spdr_calculations")
public class SpdrCalculation {

    @Id
    private Date date;
    @Column(name = "base_symbol")
    private String baseSymbol;
    @Column(name = "corr_symbol")
    private String corrSymbol;
    @Column(nullable = false)
    private Float correlation;

    public Boolean isEmpty() {
        if (this.date == null && this.baseSymbol == null && this.corrSymbol == null && this.correlation == null) {
            return true;
        } else {
            return false;
        }
    }
}
