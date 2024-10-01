package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.exception.ParkingLotAssignmentException;
import org.example.exception.ParkingSpotNotFoundException;
import org.example.exception.TicketNotFoundException;
import org.example.exception.TicketNullException;

import java.util.List;

public interface Attendant {

    default Ticket park(List<ParkingLot> parkingLots, Vehicle vehicle) {
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

    default Vehicle unPark(List<ParkingLot> parkingLots, Ticket ticket) {
        if (ticket == null) throw new TicketNullException("Ticket cannot be null");

        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.contains(ticket)) {
                return parkingLot.unPark(ticket);
            }
        }
        throw new TicketNotFoundException("Ticket not found in assigned parking lot: " + ticket);
    }

    default boolean isVehicleParked(List<ParkingLot> parkingLots, Vehicle vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.contains(vehicle)) {
                return true;
            }
        }
        return false;
    }
}
