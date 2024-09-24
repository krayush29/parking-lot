package org.example.service;

import org.example.entity.ParkingSpot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSpotTest {

    @Test
    public void TestParkingSpotAvailable() {
        ParkingSpot parkingSpot = new ParkingSpot();
        assertTrue(parkingSpot.isAvailable());
    }
}