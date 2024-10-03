package org.example.entity.strategy;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.exception.ParkingLotAssignmentException;
import org.example.exception.ParkingSpotNotFoundException;

import java.util.List;

public class SmartParkStrategy implements ParkStrategy {

    @Override
    public Ticket park(List<ParkingLot> parkingLots, Vehicle vehicle) {
        if (isVehicleParked(parkingLots, vehicle)) {
            throw new ParkingLotAssignmentException("Vehicle already parked : " + vehicle);
        }

        // Park vehicle in parkingLot which has more available spots in parkingLot
        ParkingLot parkingLot = null;
        int availableSpots = 0;
        for (ParkingLot lot : parkingLots) {
            if (lot.getAvailableSpots() > availableSpots) {
                parkingLot = lot;
                availableSpots = lot.getAvailableSpots();
            }
        }

        if (parkingLot == null) throw new ParkingSpotNotFoundException("No parking spot available");

        return parkingLot.park(vehicle);
    }
}
