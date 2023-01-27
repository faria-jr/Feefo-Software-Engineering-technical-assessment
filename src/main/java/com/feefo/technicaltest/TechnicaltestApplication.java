package com.feefo.technicaltest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.feefo.technicaltest"})
public class TechnicaltestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnicaltestApplication.class, args);
    }

}
