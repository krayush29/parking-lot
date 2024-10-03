package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.entity.role.implementation.Owner;
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
        Owner owner = new Owner();
        SmartAttendant smartAttendant = new SmartAttendant();

        ParkingLot parkingLot = owner.createParkingLot(5);
        assertDoesNotThrow(() -> owner.assign(smartAttendant, parkingLot));
    }

    @Test
    public void TestSmartAttendantToUnParkVehicleWithTicket() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        SmartAttendant smartAttendant = new SmartAttendant();
        owner.assign(smartAttendant, parkingLot);

        Vehicle vehicle = new Vehicle(anyString(), any(), any());

        Ticket ticket = smartAttendant.park(vehicle);
        Vehicle unparkedVehicle = smartAttendant.unPark(ticket);

        assertEquals(unparkedVehicle, vehicle);
    }

    @Test
    public void TestSmartAttendantToParkVehicleEvenlyDistributed() {
        // F = spot available
        // T =  spot unavailable
        // Initial state
        // PL1: T T T
        // PL2: T T T

        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = owner.createParkingLot(3);
        SmartAttendant smartAttendant = new SmartAttendant();
        owner.assign(smartAttendant, firstParkingLot);
        owner.assign(smartAttendant, secondParkingLot);

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
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        SmartAttendant smartAttendant = new SmartAttendant();
        owner.assign(smartAttendant, parkingLot);

        Vehicle vehicle = new Vehicle(anyString(), any(), any());

        Ticket ticket = smartAttendant.park(vehicle);
        Vehicle unparkedVehicle = smartAttendant.unPark(ticket);

        assertEquals(unparkedVehicle, vehicle);
    }

    @Test
    public void TestExceptionWhenAllParkingLotsAreFull() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        SmartAttendant smartAttendant = new SmartAttendant();
        owner.assign(smartAttendant, parkingLot);

        smartAttendant.park(new Vehicle(anyString(), any(), any()));

        assertThrows(ParkingSpotNotFoundException.class, () -> smartAttendant.park(new Vehicle(anyString(), any(), any())));
    }
}