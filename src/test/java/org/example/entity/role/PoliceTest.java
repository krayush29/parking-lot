package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.entity.role.implementation.Owner;
import org.example.entity.role.implementation.Police;
import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PoliceTest {

    @Test
    public void testPoliceReceivesMessage() {
        Police police = spy(new Police());

        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        parkingLot.registerSubscriber(police);

        assertDoesNotThrow(() -> parkingLot.park(new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE)));
        verify(police, times(1)).update(anyString());
    }

    @Test
    public void testPoliceReceives2MessageForParkingBecameFullAndBecameAvailable() {
        Police police = new Police();

        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        parkingLot.registerSubscriber(police);

        Ticket ticket = parkingLot.park(new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE));
        parkingLot.unPark(ticket);

        assertEquals(2, police.getNotificationMessages().size());
    }

    @Test
    public void testPoliceSubscribesMultipleParkingLot() {
        Police police = spy(new Police());
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot seconParkingLot = owner.createParkingLot(1);

        firstParkingLot.registerSubscriber(police);
        seconParkingLot.registerSubscriber(police);
        firstParkingLot.park(new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE));
        seconParkingLot.park(new Vehicle("KA-05-LA-1434", VehicleType.CAR, VehicleColour.BLACK));

        assertEquals(2, police.getNotificationMessages().size());
        verify(police, times(2)).update(anyString());
    }

    @Test
    public void testZeroNotificationsIfStateRemainsUnchanged() {
        Police police = spy(new Police());

        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        parkingLot.registerSubscriber(police);

        parkingLot.park(new Vehicle("KA-01-HH-1234", VehicleType.CAR, VehicleColour.WHITE));
        parkingLot.park(new Vehicle("KA-03-QA-1214", VehicleType.CAR, VehicleColour.BLACK));

        assertEquals(0, police.getNotificationMessages().size());
        verify(police, times(0)).update(anyString());
    }
}