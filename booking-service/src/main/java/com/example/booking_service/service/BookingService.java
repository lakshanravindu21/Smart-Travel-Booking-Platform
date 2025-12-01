package com.example.booking_service.service;

import com.example.booking_service.clients.FlightClient;
import com.example.booking_service.clients.HotelClient;
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
    private final FlightClient flightClient;
    private final HotelClient hotelClient;
    private final BookingRepository bookingRepository;
    private final String userBaseUrl;
    private final String paymentBaseUrl;
    private final String notificationBaseUrl;

    public BookingService(WebClient.Builder webClientBuilder,
                          FlightClient flightClient,
                          HotelClient hotelClient,
                          BookingRepository bookingRepository,
                          @Value("${downstream.user-base-url}") String userBaseUrl,
                          @Value("${downstream.payment-base-url}") String paymentBaseUrl,
                          @Value("${downstream.notification-base-url}") String notificationBaseUrl) {

        this.webClient = webClientBuilder.build();
        this.flightClient = flightClient;
        this.hotelClient = hotelClient;
        this.bookingRepository = bookingRepository;
        this.userBaseUrl = userBaseUrl;
        this.paymentBaseUrl = paymentBaseUrl;
        this.notificationBaseUrl = notificationBaseUrl;
    }

    public Booking createBooking(BookingRequestDTO req) {

        // 1. validate user
        UserDTO user = webClient.get()
                .uri(userBaseUrl + "/api/users/{id}", req.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
        if (user == null) throw new ResourceNotFoundException("User not found with ID: " + req.getUserId());

        // 2. check flight availability
        FlightAvailabilityDTO f = flightClient.checkAvailability(req.getFlightId());
        if (f == null || !f.isAvailable()) throw new ResourceNotFoundException("Flight not available");

        // 3. check hotel availability
        HotelAvailabilityDTO h = hotelClient.checkAvailability(req.getHotelId());
        if (h == null || !h.isAvailable()) throw new ResourceNotFoundException("Hotel not available");

        // 4. calculate total price
        double total = f.getPrice() + h.getPrice();

        // 5. save initial booking into DB
        Booking booking = new Booking();
        booking.setUserId(req.getUserId());
        booking.setFlightId(req.getFlightId());
        booking.setHotelId(req.getHotelId());
        booking.setTravelDate(req.getTravelDate());
        booking.setTotalCost(total);
        booking.setStatus(Booking.Status.PENDING);

        booking = bookingRepository.save(booking);

        // 6. send to payment service
        PaymentRequestDTO payReq = new PaymentRequestDTO(booking.getId(), total);
        PaymentResponseDTO payResp = webClient.post()
                .uri(paymentBaseUrl + "/api/payments")
                .bodyValue(payReq)
                .retrieve()
                .bodyToMono(PaymentResponseDTO.class)
                .block();

        if (payResp == null || !payResp.isSuccess()) {
            booking.setStatus(Booking.Status.FAILED);
            bookingRepository.save(booking);
            throw new PaymentFailedException("Payment failed for booking ID: " + booking.getId());
        }

        // 7. send notification (ignore failure)
        NotificationRequestDTO notif =
                new NotificationRequestDTO(req.getUserId(), "Booking confirmed: " + booking.getId());
        try {
            webClient.post()
                    .uri(notificationBaseUrl + "/api/notifications/send")
                    .bodyValue(notif)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .onErrorResume(e -> null)
                    .block();
        } catch (Exception ignored) {}

        // 8. mark confirmed
        booking.setStatus(Booking.Status.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking getBooking(Long id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Booking not found with ID: " + id)
        );
    }

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public void markPaid(Long bookingId) {
        Booking b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
        b.setStatus(Booking.Status.CONFIRMED);
        bookingRepository.save(b);
    }
}
