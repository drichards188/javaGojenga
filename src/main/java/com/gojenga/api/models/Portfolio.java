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
@Table(name = "portfolio")
public class Portfolio {

    @Id
    private Integer id;
    @Column(nullable = false)
    private String username;
    private Integer amount;
    private String asset;

    public boolean isEmpty() {
        return this.username == null && this.amount == null && this.asset == null;
    }
}
