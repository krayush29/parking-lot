package org.example.entity;

import org.example.exception.ParkingLotAssignmentException;
import org.example.exception.ParkingSpotNotFoundException;
import org.example.exception.VehicleNotFoundException;

import java.util.List;

public class ParkingLotAttendant {
    private final List<ParkingLot> parkingLots;

    public ParkingLotAttendant(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public void assign(ParkingLot parkingLot) {
        if (parkingLots.contains(parkingLot)) {
            throw new ParkingLotAssignmentException("Parking lot already assigned to the attendant");
        }
        parkingLots.add(parkingLot);
    }

    public Ticket park(Vehicle vehicle) {
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotAssignmentException("Vehicle already parked : " + vehicle);
        }

        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.getNearestAvailableSpot() != null) {
                parkingLot.park(vehicle);
                return new Ticket(vehicle);
            }
        }

        throw new ParkingSpotNotFoundException("No Available Parking Spot found for Vehicle : " + vehicle);
    }

    public Vehicle unPark(Ticket ticket) {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.isVehicleParked(ticket.getVehicle())) {
                return parkingLot.unPark(ticket.getVehicle());
            }
        }
        throw new VehicleNotFoundException("Vehicle not parked : " + ticket.getVehicle());
    }

    private boolean isVehicleParked(Vehicle vehicle) {
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.isVehicleParked(vehicle)) {
                return true;
            }
        }
        return false;
    }
}
