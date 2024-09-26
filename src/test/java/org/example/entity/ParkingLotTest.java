package org.example.entity;

import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;
import org.example.exception.ParkingLotException;
import org.example.exception.ParkingSpotNotFoundException;
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
        assertDoesNotThrow(() -> new ParkingLot(1));

        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-1));

        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0));

        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(null));
    }

    @Test
    public void TestExceptionForParkAtNearestSlotWhenSpotsIsFull() {
        Vehicle firstVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);


        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park(firstVehicle);

        assertThrows(ParkingSpotNotFoundException.class, () -> parkingLot.park(secondVehicle));
    }

    @Test
    public void TestExceptionWhenNullIsParsedForParking() {
        ParkingLot parkingLot = new ParkingLot(1);

        assertThrows(VehicleNullException.class, () -> parkingLot.park(null));
    }

    @Test
    public void TestExceptionWhenNullIsParsedForUnParking() {
        ParkingLot parkingLot = new ParkingLot(1);

        assertThrows(VehicleNullException.class, () -> parkingLot.unPark(null));
    }

    @Test
    public void TestParkVehicleToNearestAvailableSpot() {
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);

        ParkingLot parkingLot = new ParkingLot(5);
        parkingLot.park(firstVehicle);
        parkingLot.park(secondVehicle);

        parkingLot.unPark(firstVehicle);

        Integer firstNearestAvailableSlot = parkingLot.getNearestAvailableSpot();
        parkingLot.park(new Vehicle(anyString(), any(), any()));
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

        assertThrows(ParkingLotException.class, () -> parkingLot.park(vehicle));
    }

    @Test
    void testIfVehicleIsReturnedAfterUnParking() {
        ParkingLot parkingLot = new ParkingLot(2);
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);
        parkingLot.park(firstVehicle);
        parkingLot.park(secondVehicle);

        Vehicle expectedVehicle = parkingLot.unPark(firstVehicle);

        assertEquals(expectedVehicle, firstVehicle);
    }
}