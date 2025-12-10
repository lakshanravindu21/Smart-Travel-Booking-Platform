package com.example.booking_service.dto;

public class PaymentCallbackDTO {
    private String status;

    public PaymentCallbackDTO() {}

    public PaymentCallbackDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
