package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.Vehicle;
import org.example.entity.role.implementation.Owner;
import org.example.entity.role.implementation.Police;
import org.example.enums.VehicleColour;
import org.example.enums.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
}