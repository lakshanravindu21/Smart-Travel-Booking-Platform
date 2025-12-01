package com.example.hotel_service.controller;

import com.example.hotel_service.dto.HotelAvailabilityDTO;
import com.example.hotel_service.model.Hotel;
import com.example.hotel_service.service.HotelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable("id") Long id) {
        return hotelService.getHotelById(id);
    }

    @GetMapping("/{id}/availability")
    public HotelAvailabilityDTO getAvailability(@PathVariable("id") Long id) {
        return hotelService.checkAvailability(id);
    }

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }
}
