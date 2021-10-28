package com.example.springboot;

import com.example.springboot.model.Apple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class SampleTest {

    @Test
    public void sampleTest(){
        final Apple apple = Apple.builder()
                .name("녹색사과")
                .color("green")
                .weight(26)
                .build();

        log.debug("사과는 = {}", apple);
        assertEquals("green", apple.getColor());
        assertEquals("red", apple.getColor());
    }


}
