package com.example.springboot;

import com.example.springboot.model.Apple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class LambdaTest {

    @Test
    public void test1(){

        Apple appleA = Apple.builder()
                .weight(50)
                .build();
        Apple appleB = Apple.builder()
                .weight(40)
                .build();

//        // 기존 코드(익명 클래스 사용)
//        Comparable<Apple> compareByWeight = new Comparable<Apple>() {
//            @Override
//            public int compareTo(Apple o1, Apple o2) {
//                return o1.getWeight().compareTo(o2.getWeight());
//            }
//        };
//
//        // 람다
//        Comparable<Apple> lambdaCompareByWeight = (Apple aa, Apple ab) -> aa.getWeight().compareTo(ab.getWeight());

//        유효한 람다 표현식
//        (parameters) -> expression
//        (parameters) -> { statements; }
//        ex)
//        (String str) -> str.length()        // return 함축되어 있음
//        (Apple a) -> a.getWeight() > 50     // 1개의 파라미터 return
//        (int x, int y) -> {                 // 2개 이상의 파라미터 void return
//            log.debug("result = {}", x * y);
//        }
//        () -> 42                            // 0개의 파라미터 return
//        (Apple a, Apple b) -> a.getWeight().compareTo(b.getWeight())
//                                            // 2개의 파라미터 return

        List<String> list = Stream.of("k", "d", "z").collect(Collectors.toList());
        log.debug("originals ={}", Arrays.asList(list));

//        list.sort((String a, String b) -> a.compareTo(b));
//        log.debug("sort 1 ={}", Arrays.asList(list));

        list.sort(String::compareToIgnoreCase);
        log.debug("sort 메서드 레퍼런스 ={}", Arrays.asList(list));


        Apple[] applesArray = {appleA, appleB};
        List<Apple> inventory = Arrays.asList(applesArray);
        inventory.sort(Comparator.comparing(Apple::getWeight)); // 메서드 레퍼런스
        log.debug("inventory sort ={}", Arrays.asList(inventory));

    }
}
