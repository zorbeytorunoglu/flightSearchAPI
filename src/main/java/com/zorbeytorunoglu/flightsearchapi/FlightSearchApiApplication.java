package com.zorbeytorunoglu.flightsearchapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.zorbeytorunoglu.flightsearchapi.repositories")
public class FlightSearchApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(FlightSearchApiApplication.class, args);

    }

}
