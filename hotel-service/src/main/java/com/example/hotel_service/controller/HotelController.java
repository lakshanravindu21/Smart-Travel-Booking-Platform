package com.example.hotel_service.controller;

import com.example.hotel_service.model.Hotel;
import com.example.hotel_service.repository.HotelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private final HotelRepository repo;
    public HotelController(HotelRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Hotel> all() { return repo.findAll(); }

    @GetMapping("/{id}/availability")
    public ResponseEntity<?> availability(@PathVariable Long id) {
        return repo.findById(id)
                .map(h -> ResponseEntity.ok(new HotelAvailabilityDTO(h.isAvailable(), h.getPrice())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Hotel create(@RequestBody Hotel h) { return repo.save(h); }

    public static class HotelAvailabilityDTO {
        public boolean available;
        public Double price;
        public HotelAvailabilityDTO(boolean available, Double price) { this.available = available; this.price = price; }
    }
}
