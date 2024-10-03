package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.entity.role.implementation.SmartAttendant;
import org.example.exception.ParkingSpotNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class SmartAttendantTest {

    @Test
    public void TestAssignParkingLotToSmartAttendant() {
        SmartAttendant parkingLotSmartAttendant = new SmartAttendant();

        ParkingLot parkingLot = new ParkingLot(5);
        assertDoesNotThrow(() -> parkingLotSmartAttendant.assign(parkingLot));
    }

    @Test
    public void TestSmartAttendantToUnParkVehicleWithTicket() {
        ParkingLot parkingLot = new ParkingLot(5);
        SmartAttendant parkingLotSmartAttendant = new SmartAttendant();
        parkingLotSmartAttendant.assign(parkingLot);

        Vehicle vehicle = new Vehicle(anyString(), any(), any());

        Ticket ticket = parkingLotSmartAttendant.park(vehicle);
        Vehicle unparkedVehicle = parkingLotSmartAttendant.unPark(ticket);

        assertEquals(unparkedVehicle, vehicle);
    }

    @Test
    public void TestSmartAttendantToParkVehicleEvenlyDistributed() {
        // F = spot available
        // T =  spot unavailable
        // Initial state
        // PL1: T T T
        // PL2: T T T

        ParkingLot firstParkingLot = new ParkingLot(3);
        ParkingLot secondParkingLot = new ParkingLot(3);
        SmartAttendant smartAttendant = new SmartAttendant();
        smartAttendant.assign(firstParkingLot);
        smartAttendant.assign(secondParkingLot);

        smartAttendant.park(new Vehicle(anyString(), any(), any()));

        assertEquals(2, firstParkingLot.getAvailableSpots());

        smartAttendant.park(new Vehicle(anyString(), any(), any()));

        // Final state
        // PL1: F T T
        // PL2: F T T

        assertEquals(2, firstParkingLot.getAvailableSpots());
        assertEquals(2, secondParkingLot.getAvailableSpots());
    }

    @Test
    public void TestSmartAttendantToUnParkVehicleWithTicketAfterSmartPark() {
        ParkingLot parkingLot = new ParkingLot(2);
        SmartAttendant parkingLotSmartAttendant = new SmartAttendant();
        parkingLotSmartAttendant.assign(parkingLot);

        Vehicle vehicle = new Vehicle(anyString(), any(), any());

        Ticket ticket = parkingLotSmartAttendant.park(vehicle);
        Vehicle unparkedVehicle = parkingLotSmartAttendant.unPark(ticket);

        assertEquals(unparkedVehicle, vehicle);
    }

    @Test
    public void TestExceptionWhenAllParkingLotsAreFull() {
        ParkingLot parkingLot = new ParkingLot(1);
        SmartAttendant parkingLotSmartAttendant = new SmartAttendant();
        parkingLotSmartAttendant.assign(parkingLot);

        parkingLotSmartAttendant.park(new Vehicle(anyString(), any(), any()));

        assertThrows(ParkingSpotNotFoundException.class, () -> parkingLotSmartAttendant.park(new Vehicle(anyString(), any(), any())));
    }
}