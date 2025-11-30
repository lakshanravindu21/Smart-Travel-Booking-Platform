package com.example.payment_service.dto;

public class PaymentRequestDTO {
  private Long bookingId;
  private double amount;

  public PaymentRequestDTO() {}
  public PaymentRequestDTO(Long bookingId, double amount) { this.bookingId = bookingId; this.amount = amount; }

  public Long getBookingId() { return bookingId; }
  public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
  public double getAmount() { return amount; }
  public void setAmount(double amount) { this.amount = amount; }
}
