package com.example.springboot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {
    private Trader trader;
    private Integer year;
    private Integer value;

    public Transaction(Trader trader, int year, int value){
        this.trader = trader;
        this.year = year;
        this.value = value;
    }
}
