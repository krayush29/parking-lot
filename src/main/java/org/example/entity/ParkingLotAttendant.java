package org.example.entity;

import lombok.Getter;
import org.example.entity.ParkingLot;

import java.util.Map;

@Getter
public class ParkingLotAttendant {
    private final Map<Integer, ParkingLot> parkingLotMap;

    public ParkingLotAttendant(Map<Integer, ParkingLot> parkingLotMap) {
        this.parkingLotMap = parkingLotMap;
    }

    public void assignParkingLot(ParkingLot parkingLot) {
        parkingLotMap.put(parkingLot.getParkingLotId(), parkingLot);
    }

}
