package com.gojenga.api.models;

import jakarta.persistence.*;
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

    @EmbeddedId
    private SpdrCalcId id;

    private Date date;

    private Float correlation;

}
