package org.example.entity;

import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;
import org.example.exception.ParkingLotAssignmentException;
import org.example.exception.ParkingSpotNotFoundException;
import org.example.exception.VehicleNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class ParkingLotAttendantTest {

    @Test
    public void TestAssignParkingLotToAttendant() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(parkingLots);

        ParkingLot parkingLot = new ParkingLot(5);
        assertDoesNotThrow(() -> parkingLotAttendant.assign(parkingLot));
    }

    @Test
    public void TestAssignSameParkingLotAgainToAttendant() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(parkingLots);
        ParkingLot parkingLot = new ParkingLot(5);
        parkingLotAttendant.assign(parkingLot);

        assertThrows(ParkingLotAssignmentException.class, () -> parkingLotAttendant.assign(parkingLot));
    }

    @Test
    public void TestAttendantToParkVehicleWithTicket() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLot parkingLot = new ParkingLot(5);
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(parkingLots);
        parkingLotAttendant.assign(parkingLot);

        Vehicle vehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);

        Ticket ticket = parkingLotAttendant.park(vehicle);
        Vehicle unparkedVehicle = parkingLotAttendant.unPark(ticket);

        assertEquals(unparkedVehicle, vehicle);
    }

    @Test
    public void TestParkAtSecondParkingLotWhenFirstParkingLotIsFull() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(parkingLots);
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
    public void TestExceptionWhenUnParkNonExistingVehicle() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(parkingLots);
        ParkingLot parkingLot = spy(new ParkingLot(2));

        when(parkingLot.isVehicleParked(any(Vehicle.class))).thenReturn(false);

        assertThrows(VehicleNotFoundException.class, () -> parkingLotAttendant.unPark(new Ticket(any(Vehicle.class))));
    }

    @Test
    public void TestExceptionWhenAllParkingLotsAreFull() {
        List<ParkingLot> parkingLots = new ArrayList<>();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(parkingLots);

        ParkingLot mockParkingLot = mock(ParkingLot.class);
        when(mockParkingLot.getNearestAvailableSpot()).thenReturn(null);

        Vehicle vehicle = new Vehicle("UP-03-AH-1440", VehicleType.CAR, VehicleColour.WHITE);

        assertThrows(ParkingSpotNotFoundException.class, () -> parkingLotAttendant.park(vehicle));
    }
}