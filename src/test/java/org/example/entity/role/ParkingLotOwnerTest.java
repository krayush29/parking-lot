package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.role.implementation.ParkingLotAttendant;
import org.example.entity.role.implementation.ParkingLotOwner;
import org.example.exception.ParkingLotAssignmentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingLotOwnerTest {

    @Test
    public void TestAssignParkingLotToAnAttendant() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();
        ParkingLot parkingLot = new ParkingLot(5);

        assertDoesNotThrow(() -> parkingLotOwner.assign(parkingLotAttendant, parkingLot));
    }

    @Test
    public void TestAssignMoreThanOneParkingLotToAnAttendant() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot SecondParkingLot = new ParkingLot(10);

        assertDoesNotThrow(() -> parkingLotOwner.assign(parkingLotAttendant, firstParkingLot));
        assertDoesNotThrow(() -> parkingLotOwner.assign(parkingLotAttendant, SecondParkingLot));
    }

    @Test
    public void TestExceptionWhenAssignParkingLotAgainToAnAttendant() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();
        ParkingLot parkingLot = new ParkingLot(5);

        assertDoesNotThrow(() -> parkingLotOwner.assign(parkingLotAttendant, parkingLot));
        assertThrows(ParkingLotAssignmentException.class, () -> parkingLotOwner.assign(parkingLotAttendant, parkingLot));
    }

    @Test
    public void TestExceptionWhenAssignNULLParkingLotToAnAttendant() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();

        assertThrows(ParkingLotAssignmentException.class, () -> parkingLotOwner.assign(parkingLotAttendant, null));
    }

    @Test
    public void TestExceptionWhenAssignParkingLotToNULLAttendant() {
        ParkingLotOwner parkingLotOwner = new ParkingLotOwner();
        ParkingLot parkingLot = new ParkingLot(5);

        assertThrows(ParkingLotAssignmentException.class, () -> parkingLotOwner.assign(null, parkingLot));
    }
}