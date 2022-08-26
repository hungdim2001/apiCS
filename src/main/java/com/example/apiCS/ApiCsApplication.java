package com.example.apiCS;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiCsApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApiCsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
