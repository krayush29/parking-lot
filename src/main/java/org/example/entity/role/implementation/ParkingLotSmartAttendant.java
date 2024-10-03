package org.example.entity.role.implementation;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.entity.role.SmartAttendant;
import org.example.exception.ParkingLotAssignmentException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotSmartAttendant implements SmartAttendant {

    private final List<ParkingLot> parkingLots;

    public ParkingLotSmartAttendant() {
        this.parkingLots = new ArrayList<>();
    }

    public void assign(ParkingLot parkingLot) {
        if (parkingLot == null) throw new ParkingLotAssignmentException("Parking lot cannot be null");
        if (parkingLots.contains(parkingLot))
            throw new ParkingLotAssignmentException("Parking lot already assigned to the attendant");

        parkingLots.add(parkingLot);
    }

    public Ticket park(Vehicle vehicle) {
        return this.park(this.parkingLots, vehicle);
    }

    public Ticket smartPark(Vehicle vehicle) {
        return this.smartPark(this.parkingLots, vehicle);
    }

    public Vehicle unPark(Ticket ticket) {
        return this.unPark(this.parkingLots, ticket);
    }
}
