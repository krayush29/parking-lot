package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.exception.ParkingLotAssignmentException;
import org.example.exception.ParkingSpotNotFoundException;

import java.util.List;

public interface SmartAttendant extends Attendant {

    default Ticket smartPark(List<ParkingLot> parkingLots, Vehicle vehicle) {
        if (isVehicleParked(parkingLots, vehicle)) {
            throw new ParkingLotAssignmentException("Vehicle already parked : " + vehicle);
        }

        // SmartParking logic
        // Implementation of SmartParking logic
        throw new ParkingSpotNotFoundException("No Available Parking Spot found for Vehicle : " + vehicle);
    }
}
