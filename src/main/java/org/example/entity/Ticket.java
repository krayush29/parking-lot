package org.example.entity;

public class Ticket {
    private final Vehicle vehicle;

    public Ticket(Vehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException("Vehicle cannot be null");

        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }
}