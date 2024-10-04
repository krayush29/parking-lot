package org.example.entity;

import org.example.entity.role.Subscriber;
import org.example.entity.role.implementation.Owner;
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
    private final List<Subscriber> subscribers;
    private boolean isFull = false;

    public ParkingLot(Integer numberOfSpots, Owner createdBy) {
        if (createdBy == null) throw new IllegalArgumentException("Owner cannot be null");

        if (numberOfSpots == null || numberOfSpots <= 0)
            throw new IllegalArgumentException("Number Of Spots cannot be null, zero or negative number");

        this.parkingSpots = new ArrayList<>();
        for (int i = 0; i < numberOfSpots; i++) {
            this.parkingSpots.add(new ParkingSpot());
        }
        this.subscribers = new ArrayList<>();
    }

    public Ticket park(Vehicle vehicle) {
        if (vehicle == null) throw new VehicleNullException("Vehicle cannot be null");
        if (contains(vehicle)) throw new ParkingLotException("Vehicle already parked : " + vehicle);

        Integer nearestAvailableSlot = getNearestAvailableSpot();
        if (nearestAvailableSlot == null)
            throw new ParkingSpotNotFoundException("No Available Parking Spot found for Vehicle : " + vehicle);

        Ticket ticket = parkingSpots.get(nearestAvailableSlot).park(vehicle);

        if (getAvailableSpots() == 0) updateStatusAndNotify(this.isFull);
        return ticket;
    }

    public Vehicle unPark(Ticket ticket) {
        if (ticket == null) throw new TicketNullException("Ticket cannot be null");
        boolean currentStatus = this.isFull;

        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.isSameTicket(ticket)) {
                Vehicle vehicle = parkingSpot.unPark(ticket);

                if (currentStatus) updateStatusAndNotify(true);
                return vehicle;
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

    public int getAvailableSpots() {
        int count = 0;
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    public void registerSubscriber(Subscriber subscriber) {
        if (subscriber == null) throw new IllegalArgumentException("Subscriber cannot be null");
        subscribers.add(subscriber);
    }

    private void updateStatusAndNotify(boolean currentStatus) {
        this.isFull = !currentStatus;

        for (Subscriber subscriber : subscribers) {
            if (this.isFull) {
                subscriber.update("Parking Lot has became Full: " + this.hashCode());
            } else {
                subscriber.update("Parking Lot has became Available: " + this.hashCode());
            }
        }
    }
}
