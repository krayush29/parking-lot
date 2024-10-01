package org.example.entity;

import org.example.exception.ParkingLotAssignmentException;
import org.example.exception.ParkingSpotNotFoundException;
import org.example.exception.TicketNotFoundException;
import org.example.exception.TicketNullException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotAttendant {
    private final List<ParkingLot> parkingLots;

    public ParkingLotAttendant() {
        this.parkingLots = new ArrayList<>();
    }

    public void assign(ParkingLot parkingLot) {
        if (parkingLot == null) throw new ParkingLotAssignmentException("Parking lot cannot be null");
        if (parkingLots.contains(parkingLot))
            throw new ParkingLotAssignmentException("Parking lot already assigned to the attendant");

        parkingLots.add(parkingLot);
    }

    public Ticket park(Vehicle vehicle) {
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotAssignmentException("Vehicle already parked : " + vehicle);
        }

        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getNearestAvailableSpot() != null) {
                return parkingLot.park(vehicle);
            }
        }

        throw new ParkingSpotNotFoundException("No Available Parking Spot found for Vehicle : " + vehicle);
    }

    public Vehicle unPark(Ticket ticket) {
        if (ticket == null) throw new TicketNullException("Ticket cannot be null");

        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.contains(ticket)) {
                return parkingLot.unPark(ticket);
            }
        }
        throw new TicketNotFoundException("Ticket not found in assigned parking lot: " + ticket);
    }

    private boolean isVehicleParked(Vehicle vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.contains(vehicle)) {
                return true;
            }
        }
        return false;
    }
}
