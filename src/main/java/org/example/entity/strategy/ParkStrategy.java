package org.example.entity.strategy;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;

import java.util.List;

public interface ParkStrategy {

    Ticket park(List<ParkingLot> parkingLots, Vehicle vehicle);

    // Helper method to check if vehicle is already parked in any of the parking lots
    default boolean isVehicleParked(List<ParkingLot> parkingLots, Vehicle vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.contains(vehicle)) {
                return true;
            }
        }
        return false;
    }
}
