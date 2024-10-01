package org.example.entity.role.implementation;

import org.example.entity.ParkingLot;
import org.example.entity.role.Attendant;
import org.example.entity.role.implementation.ParkingLotAttendant;
import org.example.exception.ParkingLotAssignmentException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotOwner implements Attendant {

    private final List<ParkingLot> parkingLots;

    public ParkingLotOwner() {
        this.parkingLots = new ArrayList<>();
    }

    public void assign(ParkingLotAttendant parkingLotAttendant, ParkingLot parkingLot) {
        if (parkingLotAttendant == null)
            throw new ParkingLotAssignmentException("Parking lot attendant cannot be null");
        if (parkingLot == null) throw new ParkingLotAssignmentException("Parking lot cannot be null");

        parkingLotAttendant.assign(parkingLot);
    }
}
