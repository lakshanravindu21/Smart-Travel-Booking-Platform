package com.example.flight_service.controller;

import com.example.flight_service.model.Flight;
import com.example.flight_service.repository.FlightRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightRepository repo;
    public FlightController(FlightRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Flight> all() { return repo.findAll(); }

    @GetMapping("/{id}/availability")
    public ResponseEntity<?> availability(@PathVariable Long id) {
        return repo.findById(id)
                .map(f -> ResponseEntity.ok(new FlightAvailabilityDTO(f.isAvailable(), f.getPrice())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Flight create(@RequestBody Flight f) { return repo.save(f); }

    // small DTO class
    public static class FlightAvailabilityDTO {
        public boolean available;
        public Double price;
        public FlightAvailabilityDTO(boolean available, Double price) { this.available = available; this.price = price; }
    }
}
