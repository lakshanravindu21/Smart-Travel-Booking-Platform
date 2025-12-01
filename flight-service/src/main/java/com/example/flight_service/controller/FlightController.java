package com.example.flight_service.controller;

import com.example.flight_service.dto.FlightAvailabilityDTO;
import com.example.flight_service.model.Flight;
import com.example.flight_service.service.FlightService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}/availability")
    public FlightAvailabilityDTO getAvailability(@PathVariable Long id) {
        return flightService.checkAvailability(id);
    }

    @PostMapping
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.createFlight(flight);
    }
}
