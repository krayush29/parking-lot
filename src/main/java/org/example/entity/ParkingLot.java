package org.example.entity;

import lombok.Getter;
import org.example.enums.VehicleColour;
import org.example.exception.ParkingException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ParkingLot {
    private Integer parkingLotId;
    private final List<ParkingSpot> parkingSpots;

    public ParkingLot(Integer numberOfSpots) {
        if(numberOfSpots == null || numberOfSpots <= 0) throw new IllegalArgumentException("Number Of Spots cannot be null, zero or negative number");

        this.parkingSpots = new ArrayList<>(numberOfSpots);
        for (int i = 0; i < numberOfSpots; i++) {
            this.parkingSpots.add(new ParkingSpot());
        }
    }

    public void parkVehicle(Vehicle vehicle){
        int nearestAvailableSlot = -1;

        for(int i=0; i<parkingSpots.size(); i++){
            if(parkingSpots.get(i).isAvailable()){
                nearestAvailableSlot = i;
                break;
            }
        }

        if(nearestAvailableSlot == -1) throw new ParkingException("No Available Parking Spot found for Vehicle : " + vehicle.getVehicleNumber());

        parkingSpots.get(nearestAvailableSlot).setVehicle(vehicle);
        parkingSpots.get(nearestAvailableSlot).setIsAvailable(false);
    }

    public int getVehicleCountByColour(VehicleColour vehicleColour) {
        int count = 0;
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (!parkingSpot.isAvailable() && parkingSpot.getVehicle().getColor().equals(vehicleColour)) {
                count++;
            }
        }
        return count;
    }

    public boolean isVehicleParked(String registrationNumber) {
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (!parkingSpot.isAvailable() && parkingSpot.getVehicle().getVehicleNumber().equals(registrationNumber)) {
                return true;
            }
        }
        return false;
    }

    public Vehicle unParkVehicle(Integer spotNumber){
        if(spotNumber >= parkingSpots.size() || spotNumber < 0) throw new IllegalArgumentException("Spot Number does not exist :" + spotNumber);
        if(parkingSpots.get(spotNumber).isAvailable()) throw new ParkingException("Spot is already available : " + spotNumber);

        Vehicle parkedVehicle = parkingSpots.get(spotNumber).getVehicle();
        parkingSpots.get(spotNumber).setVehicle(null);
        parkingSpots.get(spotNumber).setIsAvailable(true);
        return parkedVehicle;
    }
}
