package com.zorbeytorunoglu.flightsearchapi.services;

import com.zorbeytorunoglu.flightsearchapi.entities.Flight;
import com.zorbeytorunoglu.flightsearchapi.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportService airportService;

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(String flightId) {
        return flightRepository.findById(flightId).get();
    }

    public Flight addFlight(Flight flight) {
        flight.setFlightId(UUID.randomUUID().toString().split("-")[0]);
        flight.getDepartureAirport().setCity(airportService.getAirportById(flight.getDepartureAirport().getAirportId()).getCity());
        flight.getArrivalAirport().setCity(airportService.getAirportById(flight.getArrivalAirport().getAirportId()).getCity());
        return flightRepository.save(flight);
    }

    public String deleteFlightById(String flightId) {
        flightRepository.deleteById(flightId);
        return flightId + " flight deleted.";
    }

    public Flight updateFlight(Flight flightRequest) {
        Flight existingFlight = flightRepository.findById(flightRequest.getFlightId()).get();
        existingFlight.setReturnTime(flightRequest.getReturnTime());
        existingFlight.setPrice(flightRequest.getPrice());
        existingFlight.setDepartureTime(flightRequest.getDepartureTime());
        existingFlight.setArrivalAirport(flightRequest.getArrivalAirport());
        if (flightRequest.getArrivalAirport().getCity() == null) {
            existingFlight.getArrivalAirport().setCity(airportService.getAirportById(flightRequest.getArrivalAirport().getAirportId()).getCity());
        }
        existingFlight.setDepartureAirport(existingFlight.getDepartureAirport());
        if (flightRequest.getDepartureAirport().getCity() == null) {
            existingFlight.getDepartureAirport().setCity(airportService.getAirportById(flightRequest.getDepartureAirport().getAirportId()).getCity());
        }
        return flightRepository.save(existingFlight);
    }

    public List<Flight> searchFlights(String departureAirport, String arrivalAirport, String departureTime, String returnTime) {
        List<Flight> allFlights = flightRepository.findAll();

        List<Flight> matchingFlights = allFlights.stream()
                .filter(flight ->
                        flight.getDepartureAirport().getCity().equalsIgnoreCase(departureAirport) &&
                                flight.getArrivalAirport().getCity().equalsIgnoreCase(arrivalAirport) &&
                                flight.getDepartureTime().equals(departureTime) &&
                                (returnTime == null || returnTime.isEmpty() || flight.getReturnTime().equals(returnTime))
                )
                .collect(Collectors.toList());

        return matchingFlights;
    }

    public List<Flight> searchFlightsBetweenDates(String startDateStr, String endDateStr) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);

        List<Flight> allFlights = flightRepository.findAll();

        List<Flight> matchingFlights = allFlights.stream()
                .filter(flight -> isDateBetween(flight.getDepartureTime(), startDate, endDate))
                .collect(Collectors.toList());

        return matchingFlights;
    }

    private boolean isDateBetween(String dateString, LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateString, dateFormatter);
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

}
