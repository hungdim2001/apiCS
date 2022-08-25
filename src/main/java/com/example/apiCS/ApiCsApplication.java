package com.example.apiCS;

import com.example.apiCS.Repository.CategoryRepository;
import com.example.apiCS.Repository.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiCsApplication implements CommandLineRunner {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    WeaponRepository weaponRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApiCsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
