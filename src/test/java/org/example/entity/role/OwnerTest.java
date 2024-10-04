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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OwnerTest {

    @Test
    public void TestAssignParkingLotToAnAttendant() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant(new NormalParkStrategy());
        ParkingLot parkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(attendant, parkingLot));
    }

    @Test
    public void TestAssignMoreThanOneParkingLotToAnAttendant() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant(new SmartParkStrategy());
        ParkingLot firstParkingLot = owner.createParkingLot(5);
        ParkingLot SecondParkingLot = owner.createParkingLot(10);

        assertDoesNotThrow(() -> owner.assign(attendant, firstParkingLot));
        assertDoesNotThrow(() -> owner.assign(attendant, SecondParkingLot));
    }

    @Test
    public void TestExceptionWhenAssignParkingLotAgainToAnAttendant() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant(new NormalParkStrategy());
        ParkingLot parkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(attendant, parkingLot));
        assertThrows(ParkingLotAssignmentException.class, () -> owner.assign(attendant, parkingLot));
    }

    @Test
    public void TestExceptionWhenAssignNULLParkingLotToAnAttendant() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant(new NormalParkStrategy());

        assertThrows(ParkingLotAssignmentException.class, () -> owner.assign(attendant, null));
    }

    @Test
    public void TestExceptionWhenAssignParkingLotToNULLAttendant() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);

        assertThrows(ParkingLotAssignmentException.class, () -> owner.assign(null, parkingLot));
    }

    @Test
    public void TestOwnerAssignParkingLotToItself() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(owner, parkingLot));
    }

    @Test
    public void TestExceptionWhenOwnerAssignSameParkingLotToItselfAgain() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(owner, parkingLot));
        assertThrows(ParkingLotAssignmentException.class, () -> owner.assign(owner, parkingLot));
    }

    @Test
    public void TestOwnerAssignParkingLotToItselfAndParkVehicle() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);

        owner.assign(owner, parkingLot);
        assertDoesNotThrow(() -> owner.park(new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE)));
    }

    @Test
    public void TestOwnerAssignUnParkingLotToItselfAndParkVehicle() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        Vehicle vehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);

        owner.assign(owner, parkingLot);
        Ticket ticket = owner.park(vehicle);

        assertEquals(vehicle, owner.unPark(ticket));
    }

    @Test
    public void TestOwnerCanSubscribeToMultipleParkingLot() {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-05-LA-1434", VehicleType.CAR, VehicleColour.BLACK);

        firstParkingLot.registerSubscriber(owner);
        secondParkingLot.registerSubscriber(owner);

        assertDoesNotThrow(() -> owner.assign(owner, firstParkingLot));
        assertDoesNotThrow(() -> owner.assign(owner, secondParkingLot));
        Ticket firstTicket = owner.park(firstVehicle);
        Ticket secondTicket = owner.park(secondVehicle);

        assertEquals(2, owner.getNotificationMessages().size());

        assertDoesNotThrow(() -> owner.unPark(firstTicket));
        assertDoesNotThrow(() -> owner.unPark(secondTicket));

        assertEquals(4, owner.getNotificationMessages().size());
    }
}