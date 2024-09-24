package org.example.service;

import org.example.entity.ParkingLot;
import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;
import org.example.exception.ParkingException;
import org.example.entity.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ParkingLotTest {

    @Test
    public void TestCreateParkingLotWith50Spots(){
        ParkingLot parkingLot = new ParkingLot(50);

        assertEquals(50, parkingLot.getParkingSpots().size());
    }

    @Test
    public void TestExceptionForParkingSpotWithNullNegativeOrZeroSize(){
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-1));

        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0));

        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(null));
    }

    @Test
    public void TestParkVehicleForSpotNumber0(){
        Vehicle vehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        ParkingLot parkingLot = new ParkingLot(10);
        parkingLot.parkVehicle(vehicle);

        assertEquals(vehicle, parkingLot.getParkingSpots().getFirst().getVehicle());
    }

    @Test
    public void TestExceptionParkVehicleForUnavailableSpotNumber1(){
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.parkVehicle(firstVehicle);

        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);

        assertThrows(ParkingException.class, () -> parkingLot.parkVehicle(secondVehicle));
    }

    @Test
    public void TestParkVehicleToNearestAvailableSpot(){
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);

        ParkingLot parkingLot = new ParkingLot(5);
        parkingLot.parkVehicle(firstVehicle);
        parkingLot.parkVehicle(secondVehicle);

        // UnParking first vehicle to make spot 0 available
        parkingLot.unParkVehicle(0);

        Vehicle thirdVehicle = new Vehicle("KA-05-AT-1254", VehicleType.CAR, VehicleColour.RED);
        parkingLot.parkVehicle(thirdVehicle);

        assertEquals(thirdVehicle, parkingLot.getParkingSpots().getFirst().getVehicle());
    }

    @Test
    public void TestExceptionForParkAtNearestSlotWhenSpotsIsFull(){
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.WHITE);

        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.parkVehicle(firstVehicle);
        parkingLot.parkVehicle(secondVehicle);

        // As All parking spots occupied, we cannot park another vehicle
        Vehicle thirdVehicle = new Vehicle("KA-05-AT-1254", VehicleType.CAR, VehicleColour.RED);

        assertThrows(ParkingException.class, () -> parkingLot.parkVehicle(thirdVehicle));
    }

    @Test
    public void TestGetVehicleCountByColourRed(){
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);
        Vehicle thirdVehicle = new Vehicle("KA-05-AT-1254", VehicleType.CAR, VehicleColour.RED);


        ParkingLot parkingLot = new ParkingLot(5);
        parkingLot.parkVehicle(firstVehicle);
        parkingLot.parkVehicle(secondVehicle);
        parkingLot.parkVehicle(thirdVehicle);

        assertEquals(2, parkingLot.getVehicleCountByColour(VehicleColour.RED));
    }

    @Test
    public void TestVehicleWithGivenRegistrationNumberIsParked(){
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);

        ParkingLot parkingLot = new ParkingLot(5);
        parkingLot.parkVehicle(firstVehicle);
        parkingLot.parkVehicle(secondVehicle);

        assertTrue(parkingLot.isVehicleParked("KA-01-HH-1234"));
    }

    @Test
    public void TestUnParkVehicleForSpot0(){
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);

        ParkingLot parkingLot = new ParkingLot(5);
        parkingLot.parkVehicle(firstVehicle);
        parkingLot.parkVehicle(secondVehicle);

        parkingLot.unParkVehicle(0);

        assertNull(parkingLot.getParkingSpots().getFirst().getVehicle());
    }

    @Test
    void testIfVehicleIsReturnedAfterUnParkingBySyp() {
        ParkingLot parkingLot = spy(new ParkingLot(2));
        Vehicle firstVehicle = new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE);
        parkingLot.parkVehicle(firstVehicle);
        Vehicle secondVehicle = new Vehicle("KA-03-QA-1244", VehicleType.CAR, VehicleColour.RED);
        parkingLot.parkVehicle(secondVehicle);

        Vehicle expected = parkingLot.unParkVehicle(0);

        assertEquals(expected, firstVehicle);
        verify(parkingLot, times(1)).unParkVehicle(0);
    }
}