package com.example.flight_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flights")
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;
    private boolean available;
    private Double price;

    public Flight() {}
    public Flight(String flightNumber, boolean available, Double price) {
        this.flightNumber = flightNumber;
        this.available = available;
        this.price = price;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
