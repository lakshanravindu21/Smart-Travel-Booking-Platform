package com.example.booking_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String,Object>> handleAll(Exception ex) {
    Map<String,Object> body = new java.util.HashMap<>();
    body.put("timestamp", Instant.now().toString());
    body.put("error", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }
}
