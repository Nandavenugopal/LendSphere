package com.creditcore.creditcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CreditcoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditcoreApplication.class, args);
    }
}