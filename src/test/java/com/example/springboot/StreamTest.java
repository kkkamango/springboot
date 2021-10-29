package com.example.springboot;

import com.example.springboot.model.Dish;
import com.example.springboot.model.Trader;
import com.example.springboot.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class StreamTest {

    @Test
    public void streamTest1(){
        List<Dish> menu = Arrays.asList(
                Dish.builder().name("pork").vegetarian(false).calories(800).type(Dish.Type.MEAT).build()
                , Dish.builder().name("beef").vegetarian(false).calories(700).type(Dish.Type.MEAT).build()
                , Dish.builder().name("chicken").vegetarian(false).calories(400).type(Dish.Type.MEAT).build()
                , Dish.builder().name("french fries").vegetarian(true).calories(530).type(Dish.Type.OTHER).build()
                , Dish.builder().name("rice").vegetarian(true).calories(350).type(Dish.Type.OTHER).build()
                , Dish.builder().name("season fruit").vegetarian(true).calories(120).type(Dish.Type.OTHER).build()
                , Dish.builder().name("pizza").vegetarian(true).calories(550).type(Dish.Type.OTHER).build()
                , Dish.builder().name("prawns").vegetarian(false).calories(300).type(Dish.Type.FISH).build()
                , Dish.builder().name("salmon").vegetarian(false).calories(450).type(Dish.Type.FISH).build()
        );

        // 스트림 = 고정된 자료구조 (추가 제거 불가)
        List<String> dishNames = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)                     // 한 요소를 다른 요소로 변환 또는 정보 추출 (매핑)
                .limit(3)                               // 축소 (truncate)    중간연산: 연결할 수 있는 스트림 연산
                .collect(Collectors.toList());          // 다른 형식으로 변환   최종연산: 파이프라인 실행후 닫음

        log.info("menu={}", Arrays.asList(menu));
        log.info("dishNames={}", Arrays.asList(dishNames));


        List<String> titles = Arrays.asList("java8", "in", "action");
        Stream<String> titleStream = titles.stream();
//        titleStream.forEach(System.out::println);
        titleStream.forEach(log::info);
//        titleStream.forEach(System.out::println);       // IllegalStateException 스트림이 이미 소비됨


        long count = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .distinct()
                .limit(3)
                .count();

        log.info("menu count = {}", count);

        log.info("중간연산 : filter, map, limit, sorted, distinct.");
//        filter        ==> Predicate<T>
//        map           ==> Function<T, R>
//        limit
//        sorted        ==> Compartor<T>
//        distinct

        log.info("최종연산 : forEach, count, collect");
    }

    @Test
    public void streamAdvancedTest1(){
        List<Dish> menu = Arrays.asList(
                Dish.builder().name("pork").vegetarian(false).calories(800).type(Dish.Type.MEAT).build()
                , Dish.builder().name("beef").vegetarian(false).calories(700).type(Dish.Type.MEAT).build()
                , Dish.builder().name("chicken").vegetarian(false).calories(400).type(Dish.Type.MEAT).build()
                , Dish.builder().name("french fries").vegetarian(true).calories(530).type(Dish.Type.OTHER).build()
                , Dish.builder().name("rice").vegetarian(true).calories(350).type(Dish.Type.OTHER).build()
                , Dish.builder().name("season fruit").vegetarian(true).calories(120).type(Dish.Type.OTHER).build()
                , Dish.builder().name("pizza").vegetarian(true).calories(550).type(Dish.Type.OTHER).build()
                , Dish.builder().name("prawns").vegetarian(false).calories(300).type(Dish.Type.FISH).build()
                , Dish.builder().name("salmon").vegetarian(false).calories(450).type(Dish.Type.FISH).build()
        );

        List<Dish> skipList = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)                            // skip
                .collect(Collectors.toList());
        log.info("skipList = {}", Arrays.asList(skipList));

        List<Integer> lengthList = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
        log.info("lengthList = {}", Arrays.asList(lengthList));

        // 스트림 평면화 flatMap : 스트림의 각 값을 다른 스트림으로 만든 다음 모든 스트림을 하나의 스트림으로 연결
        List<String> titles = Arrays.asList("java8", "in", "action");
        List<String> uniqueStr = titles.stream()
                .map(d -> d.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        log.info("uniqueStr = {}", Arrays.asList(uniqueStr));

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
//        List<Integer> squares = numbers.stream()
//                .map(n -> n * n)
//                .collect(Collectors.toList());
//        log.info("squares = {}", Arrays.asList(squares));

        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(d -> log.info(d.getName()));


        // 리듀싱
        int product = numbers.stream()
                .reduce(0, (a, b) -> a + b); // 0: 초기값
        log.info("모든 요소의 누적값 = {}", product);

        log.info("=========================== 다양한 활용 ===========================");
        // 숫자 스트림 매핑
        int calories = menu.stream()
                .mapToInt(Dish::getCalories) // mapToDouble, mapToLong
                .sum(); // max, min, average...
        log.info("숫자 스트림 매핑 - 칼로리 합계 : {}", calories);
        
        // 객체 스트림으로 복원
        Stream<Integer> menuStream = menu.stream()
                .mapToInt(Dish::getCalories) // 스트림 -> 숫자 스트림으로 변환
                .boxed(); // 숫자 스트림 -> 스트림으로 변환

        // 숫자 범위
        long evenNumber = IntStream.range(1, 100) // 1, 100을 포함하지 않음
                .filter(n -> n % 2 == 0)
                .count();
        long evenNumber2 = IntStream.rangeClosed(1, 100) // 1, 100 포함
                .filter(n -> n % 2 == 0)
                .count();
        log.info("숫자 범위 IntStream.range = {}", evenNumber);         // 49
        log.info("숫자 범위 IntStream.rangeClosed = {}", evenNumber2);  // 50


        log.info("=========================== 스트림 만들기 ===========================");

        // 값으로 스트림 만들기
        Stream<String> valueStream = Stream.of("Java", "Lambda", "In", "Action");
        valueStream.map(String::toLowerCase)
                .forEach(log::info);

        // 빈 스트림
        Stream<Object> emptyStream = Stream.empty();

        // 배열로 스트림 만들기
        int[] numbersArray = {1, 3, 5, 7, 11, 13};
        long sumOfNumbersArray = Arrays.stream(numbersArray)     // IntStream
                .sum();
        log.info("배열로 스트림 만들기 : {}", sumOfNumbersArray);

        // 파일로 스트림 만들기

        // 함수로 무한 스트림 만들기
        List<Integer> iterateList = Stream.iterate(0, n -> n + 1)   // 순차적 연산
                .limit(10)
                .collect(Collectors.toList());
        log.info("함수로 무한 스트림 만들기 iterateList : {}", iterateList);

        List<Double> generateList = Stream.generate(Math::random)       // Supplier<T>를 인수로 받아서 새로운 값 생산
                .limit(10)
                .collect(Collectors.toList());
        log.info("함수로 무한 스트림 만들기 generateList : {}", generateList);


        // TODO chapter 6
        log.info("=========================== 스트림으로 데이터 수집 ===========================");




    }

    @Test
    public void quiz(){
        Trader raoul = new Trader("Raoul", "서울");
        Trader mario = new Trader("Mario", "대전");
        Trader alan = new Trader("Alan", "서울");
        Trader brian = new Trader("Brian", "서울");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300)
                , new Transaction(raoul, 2012, 1000)
                , new Transaction(raoul, 2011, 400)
                , new Transaction(mario, 2012, 710)
                , new Transaction(mario, 2012, 700)
                , new Transaction(alan, 2012, 950)
        );

        // 1.
        List<Transaction> orderedTransaction = transactions.stream()
                .filter(d -> d.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        log.info("orderedTransaction = {}", Arrays.asList(orderedTransaction));

        // 2.
        List<String> cities = transactions.stream()
                .map(d -> d.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        log.info("cities = {}", Arrays.asList(cities));

        // 3.
        List<String> mans = transactions.stream()
                .map(Transaction::getTrader)
                .filter(d -> "서울".equals(d.getCity()))
                .map(Trader::getName)
                .distinct()
                .sorted(Comparator.comparing(String::valueOf))
                .collect(Collectors.toList());
//        List<Trader> mans = transactions.stream()
//                .map(Transaction::getTrader)
//                .filter(d -> "서울".equals(d.getCity()))
//                .distinct()
//                .sorted(Comparator.comparing(Trader::getName))
//                .collect(Collectors.toList());
        log.info("mans = {}", Arrays.asList(mans));

        // 4. 모든 거래자의 이름을 정렬
        String alphabet = transactions.stream()
                .map(d -> d.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining());

        log.info("alphabet = {}", alphabet);

        // 5.
        boolean check = transactions.stream()
                .anyMatch(d -> "부산".equals(d.getTrader().getCity()));
        log.info("check = {}", check);

        // 6.
        transactions.stream()
                .filter(d -> "서울".equals(d.getTrader().getCity()))
                .forEach(d -> {
                    log.info("{}'s transaction value is {}", d.getTrader().getName(), d.getValue());
                });

        // 7.
        Optional<Integer> max = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        log.info("max = {}", max.get());

    }

    @Test
    public void quiz2(){
        log.info("===========================  ===========================");

    }
}
