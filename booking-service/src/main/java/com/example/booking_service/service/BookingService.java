package com.example.booking_service.service;

import com.example.booking_service.dto.*;
import com.example.booking_service.exception.PaymentFailedException;
import com.example.booking_service.exception.ResourceNotFoundException;
import com.example.booking_service.model.Booking;
import com.example.booking_service.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class BookingService {
    private final WebClient webClient;
    private final BookingRepository bookingRepository;
    private final String userBaseUrl;
    private final String paymentBaseUrl;
    private final String notificationBaseUrl;

    public BookingService(WebClient.Builder webClientBuilder,
                          BookingRepository bookingRepository,
                          @Value("${downstream.user-base-url}") String userBaseUrl,
                          @Value("${downstream.payment-base-url}") String paymentBaseUrl,
                          @Value("${downstream.notification-base-url}") String notificationBaseUrl) {
        this.webClient = webClientBuilder.build();
        this.bookingRepository = bookingRepository;
        this.userBaseUrl = userBaseUrl;
        this.paymentBaseUrl = paymentBaseUrl;
        this.notificationBaseUrl = notificationBaseUrl;
    }

    public Booking createBooking(BookingRequestDTO req) {
        // Validate user
        UserDTO user = webClient.get()
                .uri(userBaseUrl + "/api/users/{id}", req.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
        if (user == null) throw new ResourceNotFoundException("User not found");

        // Save booking
        Booking booking = new Booking();
        booking.setUserId(req.getUserId());
        booking.setFlightId(req.getFlightId());
        booking.setHotelId(req.getHotelId());
        booking.setTravelDate(req.getTravelDate());
        booking.setStatus(Booking.Status.PENDING);
        booking = bookingRepository.save(booking);

        // Payment logic (optional for assignment)
        // Notification logic (optional for assignment)

        booking.setStatus(Booking.Status.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public Booking getBooking(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }
}
