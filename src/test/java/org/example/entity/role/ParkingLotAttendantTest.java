package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.entity.role.implementation.ParkingLotAttendant;
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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class ParkingLotAttendantTest {

    @Test
    public void TestAssignParkingLotToAttendant() {
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();

        ParkingLot parkingLot = new ParkingLot(5);
        assertDoesNotThrow(() -> parkingLotAttendant.assign(parkingLot));
    }

    @Test
    public void TestExceptionAssigningNullParkingLot() {
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();

        assertThrows(ParkingLotAssignmentException.class, () -> parkingLotAttendant.assign(null));
    }

    @Test
    public void TestAssignSameParkingLotAgainToAttendant() {
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();
        ParkingLot parkingLot = new ParkingLot(5);
        parkingLotAttendant.assign(parkingLot);

        assertThrows(ParkingLotAssignmentException.class, () -> parkingLotAttendant.assign(parkingLot));
    }

    @Test
    public void TestAttendantToUnParkVehicleWithTicket() {
        ParkingLot parkingLot = new ParkingLot(5);
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();
        parkingLotAttendant.assign(parkingLot);

        Vehicle vehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);

        Ticket ticket = parkingLotAttendant.park(vehicle);
        Vehicle unparkedVehicle = parkingLotAttendant.unPark(ticket);

        assertEquals(unparkedVehicle, vehicle);
    }

    @Test
    public void TestParkAtSecondParkingLotWhenFirstParkingLotIsFull() {
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);

        parkingLotAttendant.assign(firstParkingLot);
        parkingLotAttendant.assign(secondParkingLot);

        parkingLotAttendant.park(firstVehicle);
        parkingLotAttendant.park(secondVehicle);

        assertTrue(firstParkingLot.contains(firstVehicle));
        assertTrue(secondParkingLot.contains(secondVehicle));
    }

    @Test
    public void TestExceptionWhenNonExistingVehicleUnParked() {
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLotAttendant.assign(parkingLot);

        Ticket ticket = new Ticket();

        assertThrows(TicketNotFoundException.class, () -> parkingLotAttendant.unPark(ticket));
    }

    @Test
    public void TestExceptionWhenAllParkingLotsAreFull() {
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();

        ParkingLot mockParkingLot = spy(new ParkingLot(1));
        when(mockParkingLot.getNearestAvailableSpot()).thenReturn(null);

        Vehicle vehicle = new Vehicle("UP-03-AH-1440", VehicleType.CAR, VehicleColour.WHITE);

        assertThrows(ParkingSpotNotFoundException.class, () -> parkingLotAttendant.park(vehicle));
    }

    @Test
    public void TestExceptionWhenParkingWithVehicleNull() {
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();
        ParkingLot parkingLot = new ParkingLot(2);

        parkingLotAttendant.assign(parkingLot);

        assertThrows(VehicleNullException.class, () -> parkingLotAttendant.park(null));
    }

    @Test
    public void TestExceptionWhenUnParkingWithTicketNull() {
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();
        ParkingLot parkingLot = new ParkingLot(2);

        parkingLotAttendant.assign(parkingLot);

        assertThrows(TicketNullException.class, () -> parkingLotAttendant.unPark(null));
    }
}