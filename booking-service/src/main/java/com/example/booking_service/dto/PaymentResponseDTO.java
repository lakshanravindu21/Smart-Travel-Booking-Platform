package com.example.booking_service.dto;

public class PaymentResponseDTO {
  private boolean success;
  public PaymentResponseDTO() {}
  public boolean isSuccess(){ return success; }
  public void setSuccess(boolean success){ this.success = success; }
}
