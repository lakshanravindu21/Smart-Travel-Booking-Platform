package com.example.booking_service.exception;

import java.time.Instant;

public class ErrorResponse {
    private String timestamp;
    private String message;
    private String path;
    private int status;

    public ErrorResponse(String message, String path, int status) {
        this.timestamp = Instant.now().toString();
        this.message = message;
        this.path = path;
        this.status = status;
    }

    // Getters
    public String getTimestamp() { return timestamp; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
    public int getStatus() { return status; }
}
