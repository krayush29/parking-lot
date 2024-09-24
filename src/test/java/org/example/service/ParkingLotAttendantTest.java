package org.example.service;

import org.example.entity.ParkingLot;
import org.example.entity.ParkingLotAttendant;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotAttendantTest {

    @Test
    public void TestAssignParkingLotToAttendant(){
        Map<Integer, ParkingLot> parkingLotMap = new HashMap<>();
        ParkingLotAttendant parkingLotAttendant = new ParkingLotAttendant(parkingLotMap);
        ParkingLot parkingLot = new ParkingLot(5);
        parkingLotAttendant.assignParkingLot(parkingLot);

        assertEquals(1, parkingLotAttendant.getParkingLotMap().size());
    }
}