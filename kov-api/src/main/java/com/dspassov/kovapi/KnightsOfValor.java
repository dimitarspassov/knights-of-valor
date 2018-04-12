package com.dspassov.kovapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KnightsOfValor {


    public static void main(String[] args) {
        SpringApplication.run(KnightsOfValor.class, args);
    }
}
