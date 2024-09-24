package org.example.pojo;

import lombok.Getter;
import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;

@Getter
public class Vehicle {
    private final String vehicleNumber;
    private final VehicleType vehicleType;
    private final VehicleColour color;

    public Vehicle(String vehicleNumber, VehicleType vehicleType, VehicleColour color) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.color = color;
    }
}
