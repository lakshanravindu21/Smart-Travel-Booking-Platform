package com.example.booking_service.service;

import com.example.booking_service.dto.BookingRequestDTO;
import com.example.booking_service.dto.PaymentRequestDTO;
import com.example.booking_service.dto.PaymentResponseDTO;
import com.example.booking_service.dto.UserDTO;
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

    // ---------------------------
    // 1. Create Booking (PENDING)
    // ---------------------------
    public Booking createBooking(BookingRequestDTO req) {

        // Validate user
        UserDTO user = webClient.get()
                .uri(userBaseUrl + "/api/users/{id}", req.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();

        if (user == null)
            throw new ResourceNotFoundException("User not found");

        // Save booking as PENDING first
        Booking booking = new Booking();
        booking.setUserId(req.getUserId());
        booking.setFlightId(req.getFlightId());
        booking.setHotelId(req.getHotelId());
        booking.setTravelDate(req.getTravelDate());
        booking.setStatus(Booking.Status.PENDING);

        booking = bookingRepository.save(booking);

        // Call Payment Service
        PaymentRequestDTO paymentReq = new PaymentRequestDTO(
                booking.getId(),
                1000.00
        );

        PaymentResponseDTO paymentResponse = webClient.post()
                .uri(paymentBaseUrl + "/api/payments")
                .bodyValue(paymentReq)
                .retrieve()
                .bodyToMono(PaymentResponseDTO.class)
                .block();

        // Do NOT confirm booking here!
        return booking;
    }

    // -------------------------------------------------
    // 2. Update Booking After Payment Callback (NEW)
    // -------------------------------------------------
    public void updateStatusAfterPayment(Long bookingId, String paymentStatus) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if ("SUCCESS".equals(paymentStatus)) {
            booking.setStatus(Booking.Status.CONFIRMED);
        } else {
            booking.setStatus(Booking.Status.FAILED);
        }

        bookingRepository.save(booking);
    }

    // ---------------------------
    // 3. Fetch All Bookings
    // ---------------------------
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    // ---------------------------
    // 4. Fetch Booking by ID
    // ---------------------------
    public Booking getBooking(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
    }
}
