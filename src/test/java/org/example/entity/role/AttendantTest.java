package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.entity.role.implementation.Attendant;
import org.example.entity.role.implementation.Owner;
import org.example.entity.strategy.NormalParkStrategy;
import org.example.entity.strategy.SmartParkStrategy;
import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;
import org.example.exception.ParkingLotAssignmentException;
import org.example.exception.ParkingSpotNotFoundException;
import org.example.exception.TicketNotFoundException;
import org.example.exception.TicketNullException;
import org.example.exception.VehicleNullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AttendantTest {

    @Test
    public void TestAssignParkingLotToAttendant() {
        Attendant attendant = new Attendant(new NormalParkStrategy());
        Owner owner = spy(new Owner());

        ParkingLot parkingLot = owner.createParkingLot(5);

        owner.assign(attendant, parkingLot);
        verify(owner, times(1)).assign(attendant, parkingLot);
    }

    @Test
    public void TestExceptionAssigningNullParkingLot() {
        Attendant attendant = new Attendant(new NormalParkStrategy());
        Owner owner = new Owner();

        assertThrows(ParkingLotAssignmentException.class, () -> owner.assign(attendant, null));
    }

    @Test
    public void TestAssignSameParkingLotAgainToAttendant() {
        Attendant attendant = new Attendant(new NormalParkStrategy());
        Owner owner = new Owner();

        ParkingLot parkingLot = owner.createParkingLot(5);

        owner.assign(attendant, parkingLot);

        assertThrows(ParkingLotAssignmentException.class, () -> owner.assign(attendant, parkingLot));
    }

    @Test
    public void TestAttendantToUnParkVehicleWithTicket() {
        Attendant attendant = new Attendant(new NormalParkStrategy());
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);

        owner.assign(attendant, parkingLot);

        Vehicle vehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);

        Ticket ticket = attendant.park(vehicle);
        Vehicle unparkedVehicle = attendant.unPark(ticket);

        assertEquals(unparkedVehicle, vehicle);
    }

    @Test
    public void TestParkAtSecondParkingLotWhenFirstParkingLotIsFull() {
        Attendant attendant = new Attendant(new NormalParkStrategy());
        Owner owner = new Owner();

        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);

        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);

        owner.assign(attendant, firstParkingLot);
        owner.assign(attendant, secondParkingLot);

        attendant.park(firstVehicle);
        attendant.park(secondVehicle);

        assertTrue(firstParkingLot.contains(firstVehicle));
        assertTrue(secondParkingLot.contains(secondVehicle));
    }

    @Test
    public void TestExceptionWhenNonExistingVehicleUnParked() {
        Attendant attendant = new Attendant(new NormalParkStrategy());
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        owner.assign(attendant, parkingLot);

        Ticket ticket = new Ticket();

        assertThrows(TicketNotFoundException.class, () -> attendant.unPark(ticket));
    }

    @Test
    public void TestExceptionWhenAllParkingLotsAreFull() {
        Attendant attendant = new Attendant(new NormalParkStrategy());
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);

        owner.assign(attendant, parkingLot);
        attendant.park(new Vehicle(anyString(), any(), any()));

        assertThrows(ParkingSpotNotFoundException.class, () -> attendant.park(new Vehicle(anyString(), any(), any())));
    }

    @Test
    public void TestExceptionWhenParkingWithVehicleNull() {
        Attendant attendant = new Attendant(new NormalParkStrategy());
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);

        owner.assign(attendant, parkingLot);

        assertThrows(VehicleNullException.class, () -> attendant.park(null));
    }

    @Test
    public void TestExceptionWhenUnParkingWithTicketNull() {
        Attendant attendant = new Attendant(new NormalParkStrategy());
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);

        owner.assign(attendant, parkingLot);

        assertThrows(TicketNullException.class, () -> attendant.unPark(null));
    }

    @Test
    public void TestAssignMultipleAttendantToParkingLot() {
        Attendant firstAttendant = new Attendant(new NormalParkStrategy());
        Attendant secondAttendant = new Attendant(new NormalParkStrategy());

        Owner owner = new Owner();

        ParkingLot parkingLot = owner.createParkingLot(2);

        assertDoesNotThrow(() -> owner.assign(firstAttendant, parkingLot));
        assertDoesNotThrow(() -> owner.assign(secondAttendant, parkingLot));
    }

    @Test
    public void TestAssignParkingLotToSmartAttendant() {
        Owner owner = new Owner();
        Attendant smartAttendant = new Attendant(new SmartParkStrategy());

        ParkingLot parkingLot = owner.createParkingLot(5);
        assertDoesNotThrow(() -> owner.assign(smartAttendant, parkingLot));
    }

    @Test
    public void TestSmartAttendantToUnParkVehicleWithTicket() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        Attendant smartAttendant = new Attendant(new SmartParkStrategy());
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
        Attendant smartAttendant = new Attendant(new SmartParkStrategy());
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
        Attendant smartAttendant = new Attendant(new SmartParkStrategy());
        owner.assign(smartAttendant, parkingLot);

        Vehicle vehicle = new Vehicle(anyString(), any(), any());

        Ticket ticket = smartAttendant.park(vehicle);
        Vehicle unparkedVehicle = smartAttendant.unPark(ticket);

        assertEquals(unparkedVehicle, vehicle);
    }

    @Test
    public void TestExceptionWhenAllParkingLotsAreFullForSmartAttendant() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        Attendant smartAttendant = new Attendant(new SmartParkStrategy());
        owner.assign(smartAttendant, parkingLot);

        smartAttendant.park(new Vehicle(anyString(), any(), any()));

        assertThrows(ParkingSpotNotFoundException.class, () -> smartAttendant.park(new Vehicle(anyString(), any(), any())));
    }
}