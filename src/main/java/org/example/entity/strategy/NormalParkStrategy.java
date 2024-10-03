package org.example.entity.strategy;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.exception.ParkingLotAssignmentException;
import org.example.exception.ParkingSpotNotFoundException;

import java.util.List;

public class NormalParkStrategy implements ParkStrategy {

    @Override
    public Ticket park(List<ParkingLot> parkingLots, Vehicle vehicle) {
        if (isVehicleParked(parkingLots, vehicle)) {
            throw new ParkingLotAssignmentException("Vehicle already parked : " + vehicle);
        }

        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getNearestAvailableSpot() != null) {
                return parkingLot.park(vehicle);
            }
        }

        throw new ParkingSpotNotFoundException("No Available Parking Spot found for Vehicle : " + vehicle);
    }
}
