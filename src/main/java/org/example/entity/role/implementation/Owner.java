package org.example.entity.role.implementation;

import org.example.entity.ParkingLot;
import org.example.entity.role.Attendable;
import org.example.entity.role.Notifiable;
import org.example.exception.ParkingLotAssignmentException;

import java.util.ArrayList;
import java.util.List;

public class Owner implements Attendable, Notifiable {

    private final List<ParkingLot> parkingLots;

    public Owner() {
        this.parkingLots = new ArrayList<>();
    }

    public void assign(Attendant attendant, ParkingLot parkingLot) {
        if (attendant == null)
            throw new ParkingLotAssignmentException("Parking lot attendant cannot be null");
        if (parkingLot == null) throw new ParkingLotAssignmentException("Parking lot cannot be null");

        attendant.assign(parkingLot);
    }
}
