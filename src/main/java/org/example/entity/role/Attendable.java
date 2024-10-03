package org.example.entity.role;

import org.example.entity.ParkingLot;
import org.example.entity.Ticket;
import org.example.entity.Vehicle;
import org.example.exception.TicketNotFoundException;
import org.example.exception.TicketNullException;

import java.util.List;

public interface Attendable {

    default Vehicle unPark(List<ParkingLot> parkingLots, Ticket ticket) {
        if (ticket == null) throw new TicketNullException("Ticket cannot be null");

        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.contains(ticket)) {
                return parkingLot.unPark(ticket);
            }
        }
        throw new TicketNotFoundException("Ticket not found in assigned parking lot: " + ticket);
    }
}
