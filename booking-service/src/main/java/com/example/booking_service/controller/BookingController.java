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

    public BookingController(BookingRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        booking.setStatus(Booking.Status.PENDING);
        return repo.save(booking);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") Long id,
                                                 @RequestBody Booking update) {

        return repo.findById(id).map(existing -> {

            existing.setStatus(update.getStatus());
            existing.setTotalCost(update.getTotalCost());

            Booking saved = repo.save(existing);
            return ResponseEntity.ok(saved);

        }).orElse(ResponseEntity.notFound().build());
    }
}
