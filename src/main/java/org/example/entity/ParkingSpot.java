package org.example.entity;

import org.example.enums.VehicleColour;

public class ParkingSpot {
    private Boolean isAvailable = true;
    private Vehicle vehicle;

    public Boolean isAvailable() {
        return this.isAvailable;
    }

    public void park(Vehicle vehicle) {
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
