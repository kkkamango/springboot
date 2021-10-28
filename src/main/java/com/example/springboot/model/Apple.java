package com.example.springboot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Apple {
    private String name;
    private Integer weight;
    private String color;
}
