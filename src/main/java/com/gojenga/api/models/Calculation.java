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
@Table(name = "calculations")
public class Calculation {

    @Id
    private Date startDate;
    private Date endDate;
    private String symbol;
    @Column(nullable = false)
    private Float sharpeRatio;

    public Boolean isEmpty() {
        if (this.startDate == null && this.endDate == null && this.symbol == null && this.sharpeRatio == null) {
            return true;
        } else {
            return false;
        }
    }
}