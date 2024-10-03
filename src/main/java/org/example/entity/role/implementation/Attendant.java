package org.example.entity.role.implementation;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.entity.role.Attendable;
import org.example.entity.strategy.NormalParkStrategy;
import org.example.entity.strategy.ParkStrategy;
import org.example.exception.ParkingLotAssignmentException;

import java.util.ArrayList;
import java.util.List;

public class Attendant implements Attendable {
    private final List<ParkingLot> parkingLots;
    private final ParkStrategy strategy;

    public Attendant() {
        this.parkingLots = new ArrayList<>();
        this.strategy = new NormalParkStrategy();
    }

    void assign(ParkingLot parkingLot) {
        if (parkingLot == null) throw new ParkingLotAssignmentException("Parking lot cannot be null");
        if (parkingLots.contains(parkingLot))
            throw new ParkingLotAssignmentException("Parking lot already assigned to the attendant");

        parkingLots.add(parkingLot);
    }

    public Ticket park(Vehicle vehicle) {
        return strategy.park(this.parkingLots, vehicle);
    }

    public Vehicle unPark(Ticket ticket) {
        return this.unPark(this.parkingLots, ticket);
    }
}
