package org.example.entity;

import org.example.entity.role.implementation.Owner;
import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;
import org.example.exception.ParkingLotException;
import org.example.exception.ParkingSpotNotFoundException;
import org.example.exception.TicketNullException;
import org.example.exception.VehicleNullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class ParkingLotTest {

    @Test
    public void TestExceptionForParkingSpotWithNullNegativeOrZeroSize() {
        Owner owner = new Owner();
        assertDoesNotThrow(() -> owner.createParkingLot(1));

        assertThrows(IllegalArgumentException.class, () -> owner.createParkingLot(-1));

        assertThrows(IllegalArgumentException.class, () -> owner.createParkingLot(0));
    }

    @Test
    public void TestExceptionForParkAtNearestSlotWhenSpotsIsFull() {
        Owner owner = new Owner();
        Vehicle firstVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);

        ParkingLot parkingLot = owner.createParkingLot(1);
        parkingLot.park(firstVehicle);

        assertThrows(ParkingSpotNotFoundException.class, () -> parkingLot.park(secondVehicle));
    }

    @Test
    public void TestExceptionWhenNullIsParsedForParking() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);

        assertThrows(VehicleNullException.class, () -> parkingLot.park(null));
    }

    @Test
    public void TestExceptionWhenNullIsParsedForUnParking() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);

        assertThrows(TicketNullException.class, () -> parkingLot.unPark(null));
    }

    @Test
    public void TestParkVehicleToNearestAvailableSpot() {
        Owner owner = new Owner();
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);

        ParkingLot parkingLot = owner.createParkingLot(5);
        Ticket ticket = parkingLot.park(firstVehicle);
        parkingLot.park(secondVehicle);

        parkingLot.unPark(ticket);

        Integer firstNearestAvailableSlot = parkingLot.getNearestAvailableSpot();
        parkingLot.park(new Vehicle(anyString(), any(), any()));
        Integer secondNearestAvailableSlot = parkingLot.getNearestAvailableSpot();

        assertEquals(0, firstNearestAvailableSlot);
        assertNotEquals(firstNearestAvailableSlot, secondNearestAvailableSlot);
    }

    @Test
    public void TestGetVehicleCountByColourRed() {
        Owner owner = new Owner();
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);
        Vehicle thirdVehicle = new Vehicle("KA-05-AT-1254", VehicleType.CAR, VehicleColour.RED);


        ParkingLot parkingLot = owner.createParkingLot(5);
        parkingLot.park(firstVehicle);
        parkingLot.park(secondVehicle);
        parkingLot.park(thirdVehicle);

        assertEquals(2, parkingLot.getVehicleCountByColour(VehicleColour.RED));
    }

    @Test
    public void TestGivenVehicleIsParked() {
        Owner owner = new Owner();
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);

        ParkingLot parkingLot = owner.createParkingLot(5);
        parkingLot.park(firstVehicle);
        parkingLot.park(secondVehicle);

        assertTrue(parkingLot.contains(firstVehicle));
        assertTrue(parkingLot.contains(secondVehicle));
    }

    @Test
    public void TestExceptionWhenSameVehicleIsParkedAgain() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        Vehicle vehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);

        parkingLot.park(vehicle);

        assertThrows(ParkingLotException.class, () -> parkingLot.park(vehicle));
    }

    @Test
    void testIfVehicleIsReturnedAfterUnParking() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);
        Ticket ticket = parkingLot.park(firstVehicle);
        parkingLot.park(secondVehicle);

        Vehicle expectedVehicle = parkingLot.unPark(ticket);

        assertEquals(expectedVehicle, firstVehicle);
    }

    // Notification

    @Test
    public void TestNotifyOwnerWhenParkingLotIsFull() {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        Vehicle vehicle = new Vehicle(anyString(), any(), any());
        parkingLot.registerSubscriber(owner);

        assertDoesNotThrow(() -> parkingLot.park(vehicle));
    }

    @Test
    public void TestNotifyMoreThanOneOwnerWhenParkingLotIsFull() {
        Owner firstOwner = new Owner();
        Owner secondOwner = new Owner();

        ParkingLot parkingLot = firstOwner.createParkingLot(1);

        Vehicle vehicle = new Vehicle(anyString(), any(), any());
        parkingLot.registerSubscriber(firstOwner);
        parkingLot.registerSubscriber(secondOwner);

        assertDoesNotThrow(() -> parkingLot.park(vehicle));
    }
}