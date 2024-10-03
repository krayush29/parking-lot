package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.entity.role.implementation.ParkingLotSmartAttendant;
import org.example.exception.ParkingSpotNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class ParkingLotSmartAttendantTest {

    @Test
    public void TestAssignParkingLotToSmartAttendant() {
        ParkingLotSmartAttendant parkingLotSmartAttendant = new ParkingLotSmartAttendant();

        ParkingLot parkingLot = new ParkingLot(5);
        assertDoesNotThrow(() -> parkingLotSmartAttendant.assign(parkingLot));
    }

    @Test
    public void TestSmartAttendantToUnParkVehicleWithTicket() {
        ParkingLot parkingLot = new ParkingLot(5);
        ParkingLotSmartAttendant parkingLotSmartAttendant = new ParkingLotSmartAttendant();
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
        // PL1: T T T T
        // PL2: T T T

        ParkingLot firstParkingLot = new ParkingLot(4);
        ParkingLot secondParkingLot = new ParkingLot(3);
        ParkingLotSmartAttendant parkingLotSmartAttendant = new ParkingLotSmartAttendant();
        parkingLotSmartAttendant.assign(firstParkingLot);
        parkingLotSmartAttendant.assign(secondParkingLot);

        parkingLotSmartAttendant.park(new Vehicle(anyString(), any(), any()));
        parkingLotSmartAttendant.park(new Vehicle(anyString(), any(), any()));

        assertEquals(2, firstParkingLot.getAvailableSpots());

        parkingLotSmartAttendant.smartPark(new Vehicle(anyString(), any(), any()));

        // Final state
        // PL1: F F T T
        // PL2: F T T

        assertEquals(2, firstParkingLot.getAvailableSpots());
        assertEquals(2, secondParkingLot.getAvailableSpots());
    }

    @Test
    public void TestSmartAttendantToUnParkVehicleWithTicketAfterSmartPark() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingLotSmartAttendant parkingLotSmartAttendant = new ParkingLotSmartAttendant();
        parkingLotSmartAttendant.assign(parkingLot);

        Vehicle vehicle = new Vehicle(anyString(), any(), any());

        Ticket ticket = parkingLotSmartAttendant.smartPark(vehicle);
        Vehicle unparkedVehicle = parkingLotSmartAttendant.unPark(ticket);

        assertEquals(unparkedVehicle, vehicle);
    }

    @Test
    public void TestExceptionWhenAllParkingLotsAreFull() {
        ParkingLot parkingLot = new ParkingLot(1);
        ParkingLotSmartAttendant parkingLotSmartAttendant = new ParkingLotSmartAttendant();
        parkingLotSmartAttendant.assign(parkingLot);

        parkingLotSmartAttendant.smartPark(new Vehicle(anyString(), any(), any()));

        assertThrows(ParkingSpotNotFoundException.class, () -> parkingLotSmartAttendant.smartPark(new Vehicle(anyString(), any(), any())));
    }
}