package org.example.entity;

import org.example.exception.ParkingLotAssignmentException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingLotAttendantTest {

    @Test
    public void TestAssignParkingLotToAttendant() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(parkingLots);

        ParkingLot parkingLot = new ParkingLot(5);
        parkingLotAttendant.assignParkingLot(parkingLot);


        assertEquals(1, parkingLotAttendant.getParkingLots().size());
    }

    @Test
    public void TestAssignSameParkingLotAgainToAttendant() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(parkingLots);
        ParkingLot parkingLot = new ParkingLot(5);
        parkingLotAttendant.assignParkingLot(parkingLot);

        assertThrows(ParkingLotAssignmentException.class, () -> parkingLotAttendant.assignParkingLot(parkingLot));
    }

//    @Test
//    public void TestAttendantToParkVehicle() {
//        List<ParkingLot> parkingLots = new ArrayList<>();
//        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(parkingLots);
//
//        ParkingLot parkingLot = new ParkingLot(1,5);
//        parkingLotAttendant.assignParkingLot(parkingLot);
//
//        Vehicle vehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
//
//        Ticket ticket = parkingLotAttendant.parkVehicle(vehicle);
//
//        assertEquals(ticket.getVehicleNumber(), vehicle.getVehicleNumber());
//    }
}