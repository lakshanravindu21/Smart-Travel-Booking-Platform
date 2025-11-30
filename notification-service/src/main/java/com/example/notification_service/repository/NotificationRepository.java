package com.example.notification_service.repository;

import com.example.notification_service.model.NotificationEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntry, Long> {}
