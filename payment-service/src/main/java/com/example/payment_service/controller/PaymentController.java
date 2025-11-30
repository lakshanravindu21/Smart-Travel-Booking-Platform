package com.example.payment_service.controller;

import com.example.payment_service.model.Payment;
import com.example.payment_service.repository.PaymentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentRepository repo;
    private final WebClient webClient = WebClient.create();

    public PaymentController(PaymentRepository repo) { this.repo = repo; }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment p) {
        p.setStatus("SUCCESS"); // or simulate failure
        Payment saved = repo.save(p);

        // callback to booking service (WebClient)
        try {
            webClient.post()
                .uri("http://localhost:8084/api/bookings/" + saved.getBookingId() + "/payment-callback")
                .bodyValue(saved)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
        } catch (Exception ex) {
            // log but don't fail payment save
        }

        return ResponseEntity.ok(saved);
    }
}
