package com.example.texnoeracrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TexnoeraCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(TexnoeraCrmApplication.class, args);
    }

}
