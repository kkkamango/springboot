package com.example.springboot.controller;

import com.example.springboot.model.Apple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class ViewController {

    @RequestMapping("/apple")
    public String getSample(Model model){
        Apple b = Apple.builder()
                .color("red")
                .name("빨간사과")
                .weight(30)
                .build();

        log.info("appple is {}", b);
        model.addAttribute("apple", b);
        return "view/apple";
    }
}
