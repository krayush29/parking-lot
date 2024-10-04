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
import java.util.List;

public class Owner implements Attendable, Subscriber {

    private final List<ParkingLot> parkingLots;
    private final ParkStrategy strategy;

    public Owner() {
        this.parkingLots = new ArrayList<>();
        this.strategy = new NormalParkStrategy();
    }

    public void assign(Attendable attendable, ParkingLot parkingLot) {
        if (attendable == null) throw new ParkingLotAssignmentException("User cannot be null");

        switch (attendable) {
            case Owner owner -> {
                if (this != owner) {
                    throw new ParkingLotAssignmentException("Owner cannot assign parking lot to another owner");
                }

                this.parkingLots.add(parkingLot);
            }
            case Attendant attendant -> attendant.assign(parkingLot);
            default -> {
                throw new ParkingLotAssignmentException("Invalid User type");
            }
        }
    }

    public Ticket park(Vehicle vehicle) {
        return strategy.park(this.parkingLots, vehicle);
    }

    public ParkingLot createParkingLot(int numberOfParkingSpots) {
        return new ParkingLot(numberOfParkingSpots, this);
    }

    @Override
    public void update(String message) {
        System.out.println("Owner received message: " + message);
    }
}
