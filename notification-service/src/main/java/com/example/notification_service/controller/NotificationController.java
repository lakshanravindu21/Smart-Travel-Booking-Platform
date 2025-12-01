package com.example.notification_service.controller;

import com.example.notification_service.dto.NotificationRequestDTO;
import com.example.notification_service.model.NotificationEntry;
import com.example.notification_service.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationEntry> send(@RequestBody NotificationRequestDTO dto) {
        NotificationEntry saved = notificationService.sendNotification(dto);
        return ResponseEntity.ok(saved);
    }
}
