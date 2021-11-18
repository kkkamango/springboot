package com.example.springboot.controller;

import com.example.springboot.model.Apple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/ecma6**")
    public String getEcma6(HttpServletRequest request) {

        log.debug("ECMA_VIEW request.getServletPath {}", request.getServletPath());
        StringBuilder sb = new StringBuilder(request.getServletPath());

        sb.insert(0, "view");
        log.debug("ECMA_VIEW is {}", sb.toString());

        return sb.toString();
    }
}
