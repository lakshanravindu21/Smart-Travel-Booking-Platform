package com.example.hotel_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hotels")
public class Hotel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean available;
    private Double price;

    public Hotel() {}
    public Hotel(String name, boolean available, Double price) { this.name = name; this.available = available; this.price = price; }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
