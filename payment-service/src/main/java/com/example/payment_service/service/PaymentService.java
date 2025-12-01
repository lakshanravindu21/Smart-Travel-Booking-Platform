package com.example.payment_service.service;

import com.example.payment_service.dto.PaymentRequestDTO;
import com.example.payment_service.dto.PaymentResponseDTO;
import com.example.payment_service.exception.ResourceNotFoundException;
import com.example.payment_service.model.Payment;
import com.example.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository repo;
    private final WebClient webClient;

    public PaymentService(PaymentRepository repo, WebClient.Builder webClientBuilder) {
        this.repo = repo;
        this.webClient = webClientBuilder.build();
    }

    public PaymentResponseDTO processPayment(PaymentRequestDTO dto) {
        Payment payment = new Payment();
        payment.setBookingId(dto.getBookingId());
        payment.setAmount(dto.getAmount());
        payment.setStatus("SUCCESS"); // simulate success
        payment.setCreatedAt(LocalDateTime.now());

        Payment saved = repo.save(payment);

        // Optional callback to booking service
        try {
            webClient.post()
                    .uri("http://localhost:8084/api/bookings/" + saved.getBookingId() + "/payment-callback")
                    .bodyValue(saved)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .subscribe();
        } catch (Exception ignored) {}

        return new PaymentResponseDTO(saved.getStatus().equals("SUCCESS"));
    }

    public Payment getPayment(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }
}
