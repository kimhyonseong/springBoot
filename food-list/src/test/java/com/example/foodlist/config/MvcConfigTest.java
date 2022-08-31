package com.example.foodlist.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MvcConfigTest {

    @Test
    void pathTest() {
        final Path FILE_ROOT = Paths.get("./images/tmp/").toAbsolutePath().normalize();

        System.out.println("Paths / : " + Paths.get("./").toAbsolutePath());
        System.out.println("Paths : " + FILE_ROOT);
    }
}