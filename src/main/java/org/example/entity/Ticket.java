package org.example.entity;

import lombok.Data;

@Data
public class Ticket {
    private Vehicle vehicle;

    public Ticket(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}