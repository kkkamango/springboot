package com.example.springboot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Dish {
    private String name;
    private boolean vegetarian;
    private Integer calories;
    private Type type;

    public enum Type {
        MEAT, FISH, OTHER
    }
}
