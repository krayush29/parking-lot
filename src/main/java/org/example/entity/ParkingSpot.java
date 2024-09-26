package org.example.entity;

import org.example.enums.VehicleColour;
import org.example.exception.VehicleNullException;

public class ParkingSpot {
    private Boolean isAvailable = true;
    private Vehicle vehicle;

    public Boolean isAvailable() {
        return this.isAvailable;
    }

    public void park(Vehicle vehicle) {
        if(vehicle == null) {
            throw new VehicleNullException("Vehicle cannot be null");
        }

        this.vehicle = vehicle;
        this.isAvailable = false;
    }

    public void unPark() {
        this.vehicle = null;
        this.isAvailable = true;
    }

    public boolean isSameVehicle(Vehicle vehicle) {
        return this.vehicle.equals(vehicle);
    }

    public boolean isSameVehicleColour(VehicleColour vehicleColour) {
        return this.vehicle.isSameColour(vehicleColour);
    }
}
