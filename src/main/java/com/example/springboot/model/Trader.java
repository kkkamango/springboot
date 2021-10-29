package com.example.springboot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Trader {
    private String name;
    private String city;

    public Trader(String name, String city){
        this.name = name;
        this.city = city;
    }

}
