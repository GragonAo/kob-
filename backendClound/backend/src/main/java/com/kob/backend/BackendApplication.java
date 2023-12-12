package com.kob.backend;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.pojo.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        System.out.println("StartBackendServiceS");
    }

}
