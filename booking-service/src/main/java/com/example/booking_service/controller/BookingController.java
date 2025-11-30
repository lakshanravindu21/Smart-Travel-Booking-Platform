package com.example.booking_service.controller;

import com.example.booking_service.model.Booking;
import com.example.booking_service.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository repo;
    public BookingController(BookingRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Booking> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Booking create(@RequestBody Booking b) {
        // in the real orchestrator you would set PENDING and later update
        b.setStatus(Booking.Status.PENDING);
        return repo.save(b);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable Long id, @RequestBody Booking update) {
        return repo.findById(id).map(b -> {
            // copy fields you need
            b.setStatus(update.getStatus());
            b.setTotalCost(update.getTotalCost());
            Booking saved = repo.save(b);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }
}
