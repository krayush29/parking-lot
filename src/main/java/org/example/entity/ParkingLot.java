package org.example.entity;

import org.example.enums.VehicleColour;
import org.example.exception.ParkingLotException;
import org.example.exception.ParkingSpotNotFoundException;
import org.example.exception.TicketNotFoundException;
import org.example.exception.TicketNullException;
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

    public Ticket park(Vehicle vehicle) {
        if (vehicle == null) throw new VehicleNullException("Vehicle cannot be null");
        if (contains(vehicle)) throw new ParkingLotException("Vehicle already parked : " + vehicle);

        Integer nearestAvailableSlot = getNearestAvailableSpot();
        if (nearestAvailableSlot == null)
            throw new ParkingSpotNotFoundException("No Available Parking Spot found for Vehicle : " + vehicle);

        return parkingSpots.get(nearestAvailableSlot).park(vehicle);
    }

    public Vehicle unPark(Ticket ticket) {
        if (ticket == null) throw new TicketNullException("Ticket cannot be null");

        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.isSameTicket(ticket)) {
                return parkingSpot.unPark(ticket);
            }
        }
        throw new TicketNotFoundException("Ticket is not associated any assigned parking spot: " + ticket);
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

    public Integer getNearestAvailableSpot() {
        for (int i = 0; i < parkingSpots.size(); i++) {
            if (parkingSpots.get(i).isAvailable()) {
                return i;
            }
        }
        return null;
    }

    public boolean contains(Ticket ticket) {
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.isSameTicket(ticket)) return true;
        }
        return false;
    }

    public boolean contains(Vehicle vehicle) {
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.isSameVehicle(vehicle)) return true;
        }
        return false;
    }
}
