package com.example.booking_service.clients;

import com.example.booking_service.dto.HotelAvailabilityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hotel-service", url = "${downstream.hotel-base-url}")
public interface HotelClient {
  @GetMapping("/api/hotels/{id}/availability")
  HotelAvailabilityDTO checkAvailability(@PathVariable("id") Long id);
}
