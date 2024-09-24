package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.Vehicle;

@Setter
@Getter
public class ParkingSpot {

    private Boolean isAvailable = true;
    private Vehicle vehicle;

    public Boolean isAvailable() {
        return this.isAvailable;
    }
}
