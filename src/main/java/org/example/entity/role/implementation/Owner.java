package org.example.entity.role.implementation;

import org.example.entity.ParkingLot;
import org.example.entity.role.Attendable;
import org.example.exception.ParkingLotAssignmentException;

import java.util.ArrayList;
import java.util.List;

public class Owner implements Attendable {

    private final List<ParkingLot> parkingLots;

    public Owner() {
        this.parkingLots = new ArrayList<>();
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
            case SmartAttendant smartAttendant -> smartAttendant.assign(parkingLot);
            default -> {
                throw new ParkingLotAssignmentException("Invalid User type");
            }
        }
    }

    public ParkingLot createParkingLot(int numberOfParkingSpots) {
        return new ParkingLot(numberOfParkingSpots, this);
    }
}
