package com.example.flight_service.dto;

public class FlightAvailabilityDTO {
  private boolean available;
  private double price;

  public FlightAvailabilityDTO() {}
  public FlightAvailabilityDTO(boolean available, double price) {
    this.available = available; this.price = price;
  }
  public boolean isAvailable() { return available; }
  public void setAvailable(boolean available) { this.available = available; }
  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }
}
