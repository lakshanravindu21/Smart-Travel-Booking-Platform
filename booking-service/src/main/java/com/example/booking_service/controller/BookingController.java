package com.example.booking_service.controller;

import com.example.booking_service.dto.BookingRequestDTO;
import com.example.booking_service.model.Booking;
import com.example.booking_service.service.BookingService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ------------------------------------------
    // GET all bookings
    // ------------------------------------------
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAll();
    }

    // ------------------------------------------
    // GET booking by ID
    // ------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    // ------------------------------------------
    // POST new booking (initial status = PENDING)
    // ------------------------------------------
    @PostMapping
    public Booking createBooking(@RequestBody BookingRequestDTO req) {
        return bookingService.createBooking(req);
    }

    // --------------------------------------------------------------
    // PAYMENT CALLBACK ENDPOINT (Payment Service calls this)
    // --------------------------------------------------------------
    @PostMapping("/{bookingId}/payment-callback")
    public ResponseEntity<Void> paymentCallback(
            @PathVariable("bookingId") Long bookingId,  // ✅ explicitly specify path variable name
            @RequestBody Map<String, Object> payload
    ) {
        // safely extract status
        String status = payload.get("status").toString();

        bookingService.updateStatusAfterPayment(bookingId, status);

        return ResponseEntity.ok().build();
    }
}
