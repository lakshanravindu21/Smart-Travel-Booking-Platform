package com.example.flight_service.service;

import com.example.flight_service.dto.FlightAvailabilityDTO;
import com.example.flight_service.exception.ResourceNotFoundException;
import com.example.flight_service.model.Flight;
import com.example.flight_service.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private final FlightRepository repo;

    public FlightService(FlightRepository repo) {
        this.repo = repo;
    }

    // Get all flights
    public List<Flight> getAllFlights() {
        return repo.findAll();
    }

    // Get flight by id
    public Flight getFlightById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
    }

    // Create flight
    public Flight createFlight(Flight flight) {
        return repo.save(flight);
    }

    // Check availability
    public FlightAvailabilityDTO checkAvailability(Long id) {
        Flight flight = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        return new FlightAvailabilityDTO(flight.isAvailable(), flight.getPrice());
    }
}
