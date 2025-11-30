package com.example.booking_service.dto;

import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {
  @NotNull
  private Long userId;
  @NotNull
  private Long flightId;
  @NotNull
  private Long hotelId;
  @NotNull
  private String travelDate;

  public BookingRequestDTO() {}
  public Long getUserId() { return userId; }
  public void setUserId(Long userId) { this.userId = userId; }
  public Long getFlightId() { return flightId; }
  public void setFlightId(Long flightId) { this.flightId = flightId; }
  public Long getHotelId() { return hotelId; }
  public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
  public String getTravelDate() { return travelDate; }
  public void setTravelDate(String travelDate) { this.travelDate = travelDate; }
}
