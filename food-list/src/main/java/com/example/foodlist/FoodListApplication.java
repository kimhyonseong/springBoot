package com.example.foodlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FoodListApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodListApplication.class, args);
    }

}
