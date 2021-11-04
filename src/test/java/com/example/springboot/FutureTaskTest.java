package com.example.springboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

@Slf4j
public class FutureTaskTest {
    // 비동기 테스트

    @Test
    public void futureTest(){

//        Future    java 5
//        타 thread 에서 return한 값을 메인 thread에서 받고 싶을 때

        try {
            ExecutorService es = Executors.newCachedThreadPool();
            Future<String> f = es.submit(() -> {
                Thread.sleep(2000);
                log.info("Async");
                return "Hello.";
            });

            log.info("isDone ? 1:{}", f.isDone());
//            Thread.sleep(2100);
            log.info("Exit");
            log.info("isDone ? 2:{}", f.isDone());
            log.info(f.get());      // Future.get() : 결과값을 받을때까지 대기하는 blocking 메서드

            /**               Thread.sleep(2100); 실행
             * 14:44:31.216 [main] INFO com.example.springboot.AsyncTest - isDone ? 1:false
             * 14:44:33.224 [pool-1-thread-1] INFO com.example.springboot.AsyncTest - Async
             * 14:44:33.332 [main] INFO com.example.springboot.AsyncTest - Exit
             * 14:44:33.332 [main] INFO com.example.springboot.AsyncTest - isDone ? 2:true
             * 14:44:33.332 [main] INFO com.example.springboot.AsyncTest - Hello.
             */
            /** //            Thread.sleep(2100); 비실행
             * 14:45:44.622 [main] INFO com.example.springboot.AsyncTest - isDone ? 1:false
             * 14:45:44.627 [main] INFO com.example.springboot.AsyncTest - Exit
             * 14:45:44.627 [main] INFO com.example.springboot.AsyncTest - isDone ? 2:false
             * 14:45:46.631 [pool-1-thread-1] INFO com.example.springboot.AsyncTest - Async
             * 14:45:46.631 [main] INFO com.example.springboot.AsyncTest - Hello.
             */
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void futureTaskTest(){
        // FutureTask   future 자체를 Object로 만들어줌

        try {
            ExecutorService es = Executors.newCachedThreadPool();
            FutureTask<String> f = new FutureTask<>(() -> {
                Thread.sleep(2000);
                log.info("Async");
                return "Hello.";
            });
            es.execute(f);

            log.info("isDone ? 1:{}", f.isDone());
//            Thread.sleep(2100);
            log.info("Exit");
            log.info("isDone ? 2:{}", f.isDone());
            log.info(f.get());      // Future.get() : 결과값을 받을때까지 대기하는 blocking 메서드

            /** Future와 FutureTask 결과는 같음
             * 15:04:55.964 [main] INFO com.example.springboot.AsyncTest - isDone ? 1:false
             * 15:04:57.963 [pool-1-thread-1] INFO com.example.springboot.AsyncTest - Async
             * 15:04:58.073 [main] INFO com.example.springboot.AsyncTest - Exit
             * 15:04:58.073 [main] INFO com.example.springboot.AsyncTest - isDone ? 2:true
             * 15:04:58.073 [main] INFO com.example.springboot.AsyncTest - Hello.
             */
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
