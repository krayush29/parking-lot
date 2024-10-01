package org.example.entity;

import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;
import org.example.exception.ParkingLotAssignmentException;
import org.example.exception.ParkingSpotNotFoundException;
import org.example.exception.TicketNullException;
import org.example.exception.VehicleNotFoundException;
import org.example.exception.VehicleNullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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
    public void TestAttendantToParkVehicleWithTicket() {
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

        assertTrue(firstParkingLot.isVehicleParked(firstVehicle));
        assertTrue(secondParkingLot.isVehicleParked(secondVehicle));
    }

    @Test
    public void TestExceptionWhenNonExistingVehicleUnParked() {
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLotAttendant.assign(parkingLot);

        Ticket ticket = new Ticket(new Vehicle(anyString(), any(), any()));

        assertThrows(VehicleNotFoundException.class, () -> parkingLotAttendant.unPark(ticket));
    }

    @Test
    public void TestExceptionWhenAllParkingLotsAreFull() {
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant();

        ParkingLot mockParkingLot = mock(ParkingLot.class);
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