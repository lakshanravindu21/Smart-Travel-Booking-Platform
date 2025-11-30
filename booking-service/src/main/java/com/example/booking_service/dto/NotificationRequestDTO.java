package com.example.booking_service.dto;

public class NotificationRequestDTO {
  private Long userId;
  private String message;
  public NotificationRequestDTO() {}
  public NotificationRequestDTO(Long userId, String message) { this.userId = userId; this.message = message; }
  public Long getUserId() { return userId; }
  public void setUserId(Long userId) { this.userId = userId; }
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
}
