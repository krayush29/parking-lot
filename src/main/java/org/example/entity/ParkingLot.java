package org.example.entity;

import org.example.enums.VehicleColour;
import org.example.exception.ParkingLotException;
import org.example.exception.ParkingSpotNotFoundException;
import org.example.exception.VehicleNotFoundException;
import org.example.exception.VehicleNullException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final List<ParkingSpot> parkingSpots;

    public ParkingLot(Integer numberOfSpots) {
        if (numberOfSpots == null || numberOfSpots <= 0)
            throw new IllegalArgumentException("Number Of Spots cannot be null, zero or negative number");

        this.parkingSpots = new ArrayList<>();
        for (int i = 0; i < numberOfSpots; i++) {
            this.parkingSpots.add(new ParkingSpot());
        }
    }

    public void park(Vehicle vehicle) {
        if (vehicle == null) throw new VehicleNullException("Vehicle cannot be null");
        if (isVehicleParked(vehicle)) throw new ParkingLotException("Vehicle already parked : " + vehicle);

        Integer nearestAvailableSlot = getNearestAvailableSpot();
        if (nearestAvailableSlot == null)
            throw new ParkingSpotNotFoundException("No Available Parking Spot found for Vehicle : " + vehicle);

        parkingSpots.get(nearestAvailableSlot).park(vehicle);
    }

    public Vehicle unPark(Vehicle vehicle) {
        if (vehicle == null) throw new VehicleNullException("Vehicle cannot be null");

        for (ParkingSpot parkingSpot : parkingSpots) {
            if (!parkingSpot.isAvailable() && parkingSpot.isSameVehicle(vehicle)) {
                parkingSpot.unPark();
                return vehicle;
            }
        }
        throw new VehicleNotFoundException("Vehicle not parked : " + vehicle);
    }

    public int getVehicleCountByColour(VehicleColour vehicleColour) {
        int count = 0;
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (!parkingSpot.isAvailable() && parkingSpot.isSameVehicleColour(vehicleColour)) {
                count++;
            }
        }
        return count;
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (!parkingSpot.isAvailable() && parkingSpot.isSameVehicle(vehicle)) return true;
        }
        return false;
    }

    public Integer getNearestAvailableSpot() {
        for (int i = 0; i < parkingSpots.size(); i++) {
            if (parkingSpots.get(i).isAvailable()) {
                return i;
            }
        }
        return null;
    }
}
