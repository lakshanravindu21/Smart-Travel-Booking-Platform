package com.example.hotel_service.service;

import com.example.hotel_service.dto.HotelAvailabilityDTO;
import com.example.hotel_service.exception.ResourceNotFoundException;
import com.example.hotel_service.model.Hotel;
import com.example.hotel_service.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository repo;

    public HotelService(HotelRepository repo) {
        this.repo = repo;
    }

    // Get all hotels
    public List<Hotel> getAllHotels() {
        return repo.findAll();
    }

    // Get hotel by id
    public Hotel getHotelById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
    }

    // Create hotel
    public Hotel createHotel(Hotel hotel) {
        return repo.save(hotel);
    }

    // Check availability
    public HotelAvailabilityDTO checkAvailability(Long id) {
        Hotel hotel = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        return new HotelAvailabilityDTO(hotel.isAvailable(), hotel.getPrice());
    }
}
