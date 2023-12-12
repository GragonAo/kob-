package com.kob.matchingSystem;

import com.kob.matchingSystem.service.impl.MatchingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchingSystemApplication {
    public static void main(String[] args) {
        MatchingServiceImpl.matchingPool.start();
        SpringApplication.run(MatchingSystemApplication.class,args);
        System.out.println("Start MatchingSystem");
    }
}