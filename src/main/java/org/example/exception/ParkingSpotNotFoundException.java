package org.example.exception;

public class ParkingSpotNotFoundException extends RuntimeException {
    public ParkingSpotNotFoundException(String message) {
        super(message);
    }
}
