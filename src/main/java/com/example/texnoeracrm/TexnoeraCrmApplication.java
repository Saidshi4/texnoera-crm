package com.example.texnoeracrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.example.texnoeracrm.dao.repository")
public class TexnoeraCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(TexnoeraCrmApplication.class, args);
    }

}
