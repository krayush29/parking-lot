package org.example.service;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.VehicleType;
import org.example.pojo.Vehicle;

@Setter
@Getter
public class ParkingSpot {

    private Boolean isAvailable = true;
    private Vehicle vehicle;

    public Boolean isAvailable() {
        return this.isAvailable;
    }
}
