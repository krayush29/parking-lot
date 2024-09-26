package org.example.entity;

import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;
import org.example.exception.ParkingException;
import org.example.exception.ParkingSpotNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ParkingLotTest {

    @Test
    public void TestExceptionForParkingSpotWithNullNegativeOrZeroSize() {
        assertDoesNotThrow(() -> new ParkingLot(1));

        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-1));

        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0));

        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(null));
    }

    @Test
    public void TestExceptionForParkAtNearestSlotWhenSpotsIsFull() {
        Vehicle vehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);

        ParkingLot spyParkingLot = spy(new ParkingLot(1));
        when(spyParkingLot.getNearestAvailableSpot()).thenReturn(null);

        assertThrows(ParkingSpotNotFoundException.class, () -> spyParkingLot.park(vehicle));
    }

    @Test
    public void TestParkVehicleToNearestAvailableSpot() {
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);

        ParkingLot parkingLot = spy(new ParkingLot(5));
        parkingLot.park(firstVehicle);
        parkingLot.park(secondVehicle);

        parkingLot.unPark(firstVehicle);

        Integer firstNearestAvailableSlot = parkingLot.getNearestAvailableSpot();
        parkingLot.park(any(Vehicle.class));
        Integer secondNearestAvailableSlot = parkingLot.getNearestAvailableSpot();

        assertEquals(0, firstNearestAvailableSlot);
        assertNotEquals(firstNearestAvailableSlot, secondNearestAvailableSlot);
    }

    @Test
    public void TestGetVehicleCountByColourRed() {
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);
        Vehicle thirdVehicle = new Vehicle("KA-05-AT-1254", VehicleType.CAR, VehicleColour.RED);


        ParkingLot parkingLot = new ParkingLot(5);
        parkingLot.park(firstVehicle);
        parkingLot.park(secondVehicle);
        parkingLot.park(thirdVehicle);

        assertEquals(2, parkingLot.getVehicleCountByColour(VehicleColour.RED));
    }

    @Test
    public void TestGivenVehicleIsParked() {
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);

        ParkingLot parkingLot = new ParkingLot(5);
        parkingLot.park(firstVehicle);
        parkingLot.park(secondVehicle);

        assertTrue(parkingLot.isVehicleParked(firstVehicle));
        assertTrue(parkingLot.isVehicleParked(secondVehicle));
    }

    @Test
    public void TestExceptionWhenSameVehicleIsParkedAgain() {
        ParkingLot parkingLot = new ParkingLot(5);
        Vehicle vehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);

        parkingLot.park(vehicle);

        assertThrows(ParkingException.class, () -> parkingLot.park(vehicle));
    }

    @Test
    void testIfVehicleIsReturnedAfterUnParking() {
        ParkingLot parkingLot = spy(new ParkingLot(2));
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);
        parkingLot.park(firstVehicle);
        parkingLot.park(secondVehicle);

        Vehicle expectedVehicle = parkingLot.unPark(firstVehicle);

        assertEquals(expectedVehicle, firstVehicle);
        verify(parkingLot, times(1)).unPark(firstVehicle);
    }
}