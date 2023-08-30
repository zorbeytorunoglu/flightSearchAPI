package com.zorbeytorunoglu.flightsearchapi.services;

import com.zorbeytorunoglu.flightsearchapi.entities.Airport;
import com.zorbeytorunoglu.flightsearchapi.entities.Flight;
import com.zorbeytorunoglu.flightsearchapi.repositories.AirportRepository;
import com.zorbeytorunoglu.flightsearchapi.repositories.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class MockAPIService {

    private static final Logger logger = LoggerFactory.getLogger(MockAPIService.class);
    private static final String CRON_EXPRESSION = "0 0 0 * * ?";

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    @Autowired
    public MockAPIService(FlightRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Scheduled(cron = CRON_EXPRESSION)
    public void createFlight() {
        Flight flight = generateMockFlight();
        flightRepository.save(flight);
        logger.info("Flight generated: {}", flight.getFlightId());
    }

    private Flight generateMockFlight() {
        Flight flight = new Flight();
        flight.setFlightId(UUID.randomUUID().toString().split("-")[0]);
        flight.setPrice(new Random().nextInt(20000));
        flight.setDepartureAirport(getRandomAirport());
        flight.setArrivalAirport(getRandomAirport());
        flight.setDepartureTime(String.valueOf(LocalDateTime.now()));
        return flight;
    }

    private Airport getRandomAirport() {
        List<Airport> allAirports = airportRepository.findAll();
        return allAirports.get(new Random().nextInt(allAirports.size()));
    }
}
