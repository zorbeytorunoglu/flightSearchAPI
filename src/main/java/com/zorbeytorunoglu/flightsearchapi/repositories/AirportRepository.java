package com.zorbeytorunoglu.flightsearchapi.repositories;

import com.zorbeytorunoglu.flightsearchapi.entities.Airport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends MongoRepository<Airport, String> {
}
