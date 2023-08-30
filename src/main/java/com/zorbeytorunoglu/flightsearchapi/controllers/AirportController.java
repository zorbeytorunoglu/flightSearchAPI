package com.zorbeytorunoglu.flightsearchapi.controllers;

import com.zorbeytorunoglu.flightsearchapi.entities.Airport;
import com.zorbeytorunoglu.flightsearchapi.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<List<Airport>> getAirports() {
        List<Airport> airports = airportService.getAllAirports();
        return ResponseEntity.ok(airports);
    }

    @GetMapping("/{airportId}")
    public ResponseEntity<Airport> getAirport(@PathVariable String airportId) {
        Airport airport = airportService.getAirportById(airportId);
        return airport != null ? ResponseEntity.ok(airport) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport) {
        Airport createdAirport = airportService.addAirport(airport);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAirport);
    }

    @DeleteMapping("/{airportId}")
    public ResponseEntity<String> deleteAirport(@PathVariable String airportId) {
        String result = airportService.deleteAirportById(airportId);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<Airport> modifyAirport(@RequestBody Airport airport) {
        Airport updatedAirport = airportService.updateAirport(airport);
        return updatedAirport != null ? ResponseEntity.ok(updatedAirport) : ResponseEntity.notFound().build();
    }
}
