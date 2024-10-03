package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.role.implementation.Attendant;
import org.example.entity.role.implementation.Owner;
import org.example.exception.ParkingLotAssignmentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OwnerTest {

    @Test
    public void TestAssignParkingLotToAnAttendant() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(attendant, parkingLot));
    }

    @Test
    public void TestAssignMoreThanOneParkingLotToAnAttendant() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot SecondParkingLot = new ParkingLot(10);

        assertDoesNotThrow(() -> owner.assign(attendant, firstParkingLot));
        assertDoesNotThrow(() -> owner.assign(attendant, SecondParkingLot));
    }

    @Test
    public void TestExceptionWhenAssignParkingLotAgainToAnAttendant() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();
        ParkingLot parkingLot = new ParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(attendant, parkingLot));
        assertThrows(ParkingLotAssignmentException.class, () -> owner.assign(attendant, parkingLot));
    }

    @Test
    public void TestExceptionWhenAssignNULLParkingLotToAnAttendant() {
        Owner owner = new Owner();
        Attendant attendant = new Attendant();

        assertThrows(ParkingLotAssignmentException.class, () -> owner.assign(attendant, null));
    }

    @Test
    public void TestExceptionWhenAssignParkingLotToNULLAttendant() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(5);

        assertThrows(ParkingLotAssignmentException.class, () -> owner.assign(null, parkingLot));
    }
}