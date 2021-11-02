package com.example.springboot;

import com.example.springboot.model.Shop;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
public class CompletableFutureTest {

    // 11.2 비동기 API 구현
    @Test
    public void testSync() {
        log.debug("==== start ====");

        Shop shop = new Shop("test");
        log.info("calculate = {}", shop.getPrice("sample"));

        log.debug("==== end ====");
    }

    @Test
    public void testAsync(){
        log.debug("==== start ====");

        Shop shop = new Shop();
        // 상점에 제품가격 정보 요청
        // 가격계산이 끝나기 전에 반환
        Future<Double> future = shop.getPriceAsync("sample");

        // 가격 계산하는 동안, 다른 작업 수행
        shop.doSomething();
        try {
            // 가격정보가 없으면 블록, 가격정보 있으면 get
            log.info("calculate = {}", future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        log.debug("==== end ====");
    }

    // 11.3 비블록 코드 만들기
    @Test
    public void testNonBlockingCode(){
        // 예제 11-8 순차적, 블록 방식
        long sTime = System.nanoTime();
        String product = "myPhone";
        List<Shop> shops = Arrays.asList(
                new Shop("BestPrice")
                , new Shop("LetsSaveBig")
                , new Shop("MyFavoriteShop")
                , new Shop("BuyItAll")
        );

        List<String> prices = shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());

        log.info("findPrices is ={}", prices);
        log.info("Duration ={}", (System.nanoTime() - sTime) / 1_000_000); // 8073
    }
    @Test
    public void testNonBlockingCodeByParallel(){
        // 예제 11-10
        long sTime = System.nanoTime();
        String product = "myPhone";
        List<Shop> shops = Arrays.asList(
                new Shop("BestPrice")
                , new Shop("LetsSaveBig")
                , new Shop("MyFavoriteShop")
                , new Shop("BuyItAll")
        );

        List<String> prices = shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());

        log.info("findPrices is ={}", prices);
        log.info("Duration ={}", (System.nanoTime() - sTime) / 1_000_000); // 2018
    }

    // 11.3.2 CompletableFuture로 비동기 호출 구현하기
    @Test
    public void testAsyncByCompletableFuture(){
        // 예제 11-11
        long sTime = System.nanoTime();
        String product = "myPhone";
        List<Shop> shops = Arrays.asList(
                new Shop("BestPrice")
                , new Shop("LetsSaveBig")
                , new Shop("MyFavoriteShop")
                , new Shop("BuyItAll")
        );

//        List<String> prices = shops.parallelStream()
//                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
//                .collect(Collectors.toList());

        List<CompletableFuture<String>> cFutureList = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))
                ))
                .collect(Collectors.toList());

        List<String> prices = cFutureList.stream()
                .map(CompletableFuture::join) // 모든 비동기 동작이 끝나길 기다린다.
                .collect(Collectors.toList());

        log.info("findPrices is ={}", prices);
        // 두 map 연산을 하나의 스트림 처리 파이프라인으로 처리하지 않고
        // 두개의 스트림 파이프라인으로 처리
        // 스트림연산은 게으른 특성을 가짐 -> 동기적, 순차적
        log.info("Duration ={}", (System.nanoTime() - sTime) / 1_000_000); // 4042
        // CompletableFuture는 병렬스트림 버전에 비해 작업에 이용할 수 있는 Executor를 지정할 수 있다.
        // Executor로 스레드풀을 조절
    }
}
