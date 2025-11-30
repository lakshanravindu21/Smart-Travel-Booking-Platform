package com.example.notification_service.controller;

import com.example.notification_service.model.NotificationEntry;
import com.example.notification_service.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationRepository repo;
    public NotificationController(NotificationRepository repo) { this.repo = repo; }

    @PostMapping
    public ResponseEntity<NotificationEntry> send(@RequestBody NotificationEntry n) {
        NotificationEntry saved = repo.save(n);
        // Optionally simulate sending email by logging / printing to console
        System.out.println("Notification queued: " + saved.getMessage());
        return ResponseEntity.ok(saved);
    }
}
