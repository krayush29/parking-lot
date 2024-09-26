package org.example.entity;

import lombok.Data;

@Data
public class Ticket {
    private Integer ticketNumber;
    private Integer parkingLotId;
    private Integer parkingSpotId;
    private String vehicleNumber;
}