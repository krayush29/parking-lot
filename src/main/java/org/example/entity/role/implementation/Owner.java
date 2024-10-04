package org.example.entity.role.implementation;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.entity.role.Attendable;
import org.example.entity.role.Subscriber;
import org.example.entity.strategy.NormalParkStrategy;
import org.example.entity.strategy.ParkStrategy;
import org.example.exception.ParkingLotAssignmentException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Owner implements Attendable, Subscriber {

    private final List<ParkingLot> parkingLots;
    private final ParkStrategy strategy;
    private final Queue<String> messages;

    public Owner() {
        this.parkingLots = new ArrayList<>();
        this.strategy = new NormalParkStrategy();
        this.messages = new LinkedList<>();
    }

    public void assign(Attendable attendable, ParkingLot parkingLot) {
        if (attendable == null) throw new ParkingLotAssignmentException("User cannot be null");

        switch (attendable) {
            case Owner owner -> {
                if (this != owner)
                    throw new ParkingLotAssignmentException("Owner cannot assign parking lot to another owner");
                if (this.parkingLots.contains(parkingLot))
                    throw new ParkingLotAssignmentException("Parking lot already assigned to the owner");

                this.parkingLots.add(parkingLot);
            }
            case Attendant attendant -> attendant.assign(parkingLot);
            default -> {
                throw new ParkingLotAssignmentException("Invalid User type");
            }
        }
    }

    public ParkingLot createParkingLot(int numberOfParkingSpots) {
        return new ParkingLot(numberOfParkingSpots, this);
    }

    public Ticket park(Vehicle vehicle) {
        return strategy.park(this.parkingLots, vehicle);
    }

    public Vehicle unPark(Ticket ticket) {
        return this.unPark(this.parkingLots, ticket);
    }

    @Override
    public void update(String message) {
        this.messages.add(message);
    }

    public LinkedList<String> getNotificationMessages() {
        return (LinkedList<String>) this.messages;
    }
}
