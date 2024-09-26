package org.example.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParkingSpotTest {

    @Test
    public void TestParkingSpotAvailable() {
        ParkingSpot parkingSpot = new ParkingSpot();
        assertTrue(parkingSpot.isAvailable());
    }
}