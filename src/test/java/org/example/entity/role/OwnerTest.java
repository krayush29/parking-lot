package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.role.implementation.Attendant;
import org.example.entity.role.implementation.Owner;
import org.example.entity.strategy.NormalParkStrategy;
import org.example.entity.strategy.SmartParkStrategy;
import org.example.exception.ParkingLotAssignmentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
}