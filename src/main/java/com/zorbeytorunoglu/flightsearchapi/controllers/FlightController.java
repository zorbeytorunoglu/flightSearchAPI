package com.zorbeytorunoglu.flightsearchapi.controllers;

import com.zorbeytorunoglu.flightsearchapi.entities.Flight;
import com.zorbeytorunoglu.flightsearchapi.services.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
@Validated
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<Flight> getFlight(@PathVariable String flightId) {
        Flight flight = flightService.getFlightById(flightId);
        return flight != null ? ResponseEntity.ok(flight) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        Flight createdFlight = flightService.addFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFlight);
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable String flightId) {
        String result = flightService.deleteFlightById(flightId);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<Flight> modifyFlight(@RequestBody Flight flight) {
        Flight updatedFlight = flightService.updateFlight(flight);
        return updatedFlight != null ? ResponseEntity.ok(updatedFlight) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Flight>> findFlights(
            @RequestParam(name = "departureAirport") String departureAirport,
            @RequestParam(name = "arrivalAirport") String arrivalAirport,
            @RequestParam(name = "departureTime") String departureTime,
            @RequestParam(name = "returnTime", required = false) String returnTime
    ) {
        List<Flight> flights = flightService.searchFlights(departureAirport, arrivalAirport, departureTime, returnTime);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/searchWithDates")
    public ResponseEntity<List<Flight>> findFlightsWithDates(
            @RequestParam(name = "startDate") String startDateStr,
            @RequestParam(name = "endDate") String endDateStr
    ) {
        List<Flight> flights = flightService.searchFlightsBetweenDates(startDateStr, endDateStr);
        return ResponseEntity.ok(flights);
    }
}
