package com.example.booking_service.controller;

import com.example.booking_service.model.Booking;
import com.example.booking_service.repository.BookingRepository;
import com.example.booking_service.dto.BookingRequestDTO;
import com.example.booking_service.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @PostMapping
    public Booking createBooking(@RequestBody BookingRequestDTO req) {
        return bookingService.createBooking(req);
    }
}
