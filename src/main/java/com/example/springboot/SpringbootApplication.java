package com.example.springboot;

import com.example.springboot.model.Apple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringbootApplication.class);

    public static void main(String[] args) {

        // Lombok builder
        Apple a = Apple.builder().weight(10).build();
        Apple b = Apple.builder()
                .color("red")
                .name("빨간사과")
                .weight(30)
                .build();
        log.debug(a.toString());
        log.debug(b.toString());

        SpringApplication.run(SpringbootApplication.class, args);
    }

}
