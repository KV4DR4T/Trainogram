package com.example.trainogram;

import com.example.trainogram.util.SpringContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//TODO: add logging
//TODO: add tests(after reviewing services)
public class TrainogramApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainogramApplication.class, args);
    }

}
