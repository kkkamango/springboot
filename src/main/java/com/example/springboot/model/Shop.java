package com.example.springboot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
public class Shop {
    public Shop(){}
    public Shop(String name){
        this.name = name;
    }
    @Getter
    @Setter
    private String name;
    private double price;

    // 11.2 비동기 API 구현
    /**
     * supplyAsync로
     * getPriceAsync를 간단하게
     * @param product
     * @return
     */
    public Future<Double> getPriceAsyncByFactoryMethod(String product){
        // supplyAsync는 Supplier를 인수로 받아서 CompletableFuture를 반환
        // CompletableFuture는 Supplier를 실행해서 비동기적으로 결과를 생성
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }
    /**
     * 비동기
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync(String product){
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                // 예외 발생시 Future 종료
                futurePrice.completeExceptionally(e);
            }
            
        })
                .start();
        return futurePrice;
    }
    /**
     * 동기
     * @param product
     * @return
     */
    public double getPrice(String product){
        return calculatePrice(product);
    }

//    @XSlf4j
    public void doSomething(){
        log.info("make cotton candy");
        log.info("make ball");
        log.info("make cartoon");
        log.info("make ...");
    }
    private double calculatePrice(String product) {
        delay();
        return Math.random() * 1000 + product.charAt(1);
    }

    private void delay() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException("Exception in delay method.");
        }
    }

    // 11.3 비블록 코드 만들기
}
