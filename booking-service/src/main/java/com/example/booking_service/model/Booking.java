package com.example.booking_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {

    public enum Status { PENDING, CONFIRMED, FAILED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long flightId;
    private Long hotelId;
    private String travelDate;
    private double totalCost;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }

    public String getTravelDate() { return travelDate; }
    public void setTravelDate(String travelDate) { this.travelDate = travelDate; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
