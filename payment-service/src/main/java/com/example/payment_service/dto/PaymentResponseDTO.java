package com.example.payment_service.dto;

public class PaymentResponseDTO {
  private boolean success;
  public PaymentResponseDTO() {}
  public PaymentResponseDTO(boolean success) { this.success = success; }
  public boolean isSuccess() { return success; }
  public void setSuccess(boolean success) { this.success = success; }
}
