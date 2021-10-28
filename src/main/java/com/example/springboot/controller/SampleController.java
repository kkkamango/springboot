package com.example.springboot.controller;

import com.example.springboot.model.Apple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @GetMapping("/sample")
    public String getSample(){
        Apple b = Apple.builder()
                .color("red")
                .name("빨간사과")
                .weight(30)
                .build();
        return b.toString();
    }
}
