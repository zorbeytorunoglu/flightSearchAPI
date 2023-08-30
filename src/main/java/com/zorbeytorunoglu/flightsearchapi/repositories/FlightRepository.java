package com.zorbeytorunoglu.flightsearchapi.repositories;

import com.zorbeytorunoglu.flightsearchapi.entities.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends MongoRepository<Flight, String> {
}
