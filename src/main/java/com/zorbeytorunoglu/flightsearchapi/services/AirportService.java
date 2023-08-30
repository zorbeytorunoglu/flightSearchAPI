package com.zorbeytorunoglu.flightsearchapi.services;

import com.zorbeytorunoglu.flightsearchapi.entities.Airport;
import com.zorbeytorunoglu.flightsearchapi.repositories.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }

    public Airport addAirport(Airport airport) {
        airport.setAirportId(UUID.randomUUID().toString().split("-")[0]);
        return airportRepository.save(airport);
    }

    public Airport getAirportById(String airportId) {
        return airportRepository.findById(airportId).orElse(null);
    }

    public String deleteAirportById(String airportId) {
        airportRepository.deleteById(airportId);
        return airportId + " airport deleted.";
    }

    public Airport updateAirport(Airport airportRequest) {
        Airport existingAirport = airportRepository.findById(airportRequest.getAirportId()).orElse(null);
        if (existingAirport != null) {
            existingAirport.setCity(airportRequest.getCity());
            return airportRepository.save(existingAirport);
        }
        return null;
    }
}
