package org.example.entity;

import org.example.enums.VehicleColour;
import org.example.exception.TicketNullException;
import org.example.exception.VehicleNullException;

public class ParkingSpot {
    private Boolean isAvailable = true;
    private Vehicle vehicle;
    private Ticket ticket;

    public Boolean isAvailable() {
        return this.isAvailable;
    }

    public Ticket park(Vehicle vehicle) {
        if (vehicle == null) {
            throw new VehicleNullException("Vehicle cannot be null");
        }

        this.vehicle = vehicle;
        this.isAvailable = false;
        this.ticket = new Ticket();
        return this.ticket;
    }

    public Vehicle unPark(Ticket ticket) {
        if (ticket == null) throw new TicketNullException("Ticket cannot be null");
        if (ticket != this.ticket)
            throw new IllegalArgumentException("Ticket is not associated with this parking spot");

        Vehicle vehicle = this.vehicle;
        this.vehicle = null;
        this.ticket = null;
        this.isAvailable = true;

        return vehicle;
    }

    public boolean isSameVehicle(Vehicle vehicle) {
        if (this.vehicle == null || vehicle == null) return false;
        return this.vehicle.equals(vehicle);
    }

    public boolean isSameTicket(Ticket ticket) {
        if (this.ticket == null || ticket == null) return false;
        return this.ticket.equals(ticket);
    }

    public boolean isSameVehicleColour(VehicleColour vehicleColour) {
        return this.vehicle.isSameColour(vehicleColour);
    }
}
