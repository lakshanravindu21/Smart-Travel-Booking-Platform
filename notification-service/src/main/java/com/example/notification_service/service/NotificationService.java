package com.example.notification_service.service;

import com.example.notification_service.dto.NotificationRequestDTO;
import com.example.notification_service.model.NotificationEntry;
import com.example.notification_service.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    // Send a notification
    public NotificationEntry sendNotification(NotificationRequestDTO dto) {
        NotificationEntry entry = new NotificationEntry(dto.getUserId(), dto.getMessage());
        entry.setCreatedAt(LocalDateTime.now());
        NotificationEntry saved = repo.save(entry);

        // Optionally log to simulate sending
        System.out.println("Notification queued: " + saved.getMessage());
        return saved;
    }
}
