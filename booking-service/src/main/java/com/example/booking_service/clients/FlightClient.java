package com.example.booking_service.clients;

import com.example.booking_service.dto.FlightAvailabilityDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-service", url = "${downstream.flight-base-url}")
public interface FlightClient {
  @GetMapping("/api/flights/{id}/availability")
  FlightAvailabilityDTO checkAvailability(@PathVariable("id") Long id);
}
