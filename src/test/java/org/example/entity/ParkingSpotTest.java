package org.example.entity;

import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;
import org.example.exception.VehicleNullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParkingSpotTest {

    @Test
    public void TestParkingSpotAvailable() {
        ParkingSpot parkingSpot = new ParkingSpot();
        assertTrue(parkingSpot.isAvailable());
    }

    @Test
    public void TestExceptionWhenVehicleToBeParkedIsNull() {
        ParkingSpot parkingSpot = new ParkingSpot();
        assertThrows(VehicleNullException.class, () -> parkingSpot.park(null));
    }

    @Test
    public void TestisAvailableBecomeFalseAfterParking() {
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.park(new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE));

        assertFalse(parkingSpot.isAvailable());
    }

    @Test
    public void TestisAvailableBecomeTrueAfterUnParking() {
        ParkingSpot parkingSpot = new ParkingSpot();
        Ticket ticket = parkingSpot.park(new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE));
        parkingSpot.unPark(ticket);

        assertTrue(parkingSpot.isAvailable());
    }

    @Test
    public void TestParkedVehicleIsSameAndSpotBecomeUnavailable() {
        ParkingSpot parkingSpot = new ParkingSpot();
        Vehicle vehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        parkingSpot.park(vehicle);

        assertFalse(parkingSpot.isAvailable());
        assertTrue(parkingSpot.isSameVehicle(vehicle));
    }
}