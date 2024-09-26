package org.example.entity;

import lombok.Getter;
import org.example.exception.ParkingLotAssignmentException;

import java.util.List;

@Getter
public class ParkingLotAttendant {
    private final List<ParkingLot> parkingLots;

    public ParkingLotAttendant(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public void assignParkingLot(ParkingLot parkingLot) {
        if(parkingLots.contains(parkingLot)) {
            throw new ParkingLotAssignmentException("Parking lot already assigned to the attendant");
        }
        parkingLots.add(parkingLot);
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        return null;
    }

    public void dummyMethod() {
        return;
    }
}
