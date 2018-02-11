/**
 * 
 */
package com.mybooking.myapp;

import java.util.*;


import com.mybooking.entities.Seat;
import com.mybooking.entities.SeatHold;
import com.mybooking.entities.SeatReserve;
import com.mybooking.entities.Venue;
import com.mybooking.utilities.VenueUtil;

/**
 * @author anantha.ramamurthy
 *
 */
public class TicketServiceImpl implements TicketService {

	Venue venue;

	public TicketServiceImpl(Venue venue) {
		this.venue = venue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mybooking.myapp.TicketService#numSeatsAvailable()
	 */
	public int numSeatsAvailable() {
		return venue.getCapacity() - (VenueUtil.getTotalSeatsHeld(venue) + VenueUtil.getTotalSeatsReserved(venue));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mybooking.myapp.TicketService#reserveSeats(int, java.lang.String)
	 */
	public String reserveSeats(String seatHoldId, String customerEmail) {
		if (VenueUtil.isEmpty(seatHoldId) || VenueUtil.isEmpty(customerEmail)) {
			System.out.println(VenueUtil.INVALID_INPUT);
			return "ERROR";
		}
		if (this.venue.getSeatHolds().get(seatHoldId) == null) {
			System.out.println(VenueUtil.INVALID_HOLD_ID);
			return "ERROR";
		}
		if (!customerEmail.equalsIgnoreCase(this.venue.getSeatHolds().get(seatHoldId).getCustomerEmail())) {
			System.out.println(VenueUtil.EMAIL_ID_NOT_VALID_FOR_HOLD);
			return "ERROR";
		}
				
		synchronized (this.venue) {
			SeatReserve seatReserve = VenueUtil.holdToReserved(this.venue.getSeatHolds().get(seatHoldId));
			this.venue.getSeatReserved().put(seatHoldId, seatReserve);
			this.venue.getSeatHolds().remove(seatHoldId);
		}
		System.out.println("Booking Identifier : " + seatHoldId);
		return seatHoldId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mybooking.myapp.TicketService#findAndHoldSeats(int,
	 * java.lang.String)
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		if (numSeats > this.numSeatsAvailable()) {
			System.out.println(VenueUtil.UNAVAILABLE);
			return null;
		}

		// Loop through the seats in the Venue and block the seats available first.
		String seatHoldId = VenueUtil.getUUID().toString();

		SeatHold seatHold = new SeatHold(seatHoldId, customerEmail, numSeats);
		List<Seat> seatsHeld = seatHold.getSeatsHeld();
		
		synchronized (this.venue) {
			// Double checking availability
			if (numSeats > this.numSeatsAvailable()) {
				System.out.println(VenueUtil.UNAVAILABLE);
				return null;
			}
			for (Seat seat : this.venue.getSeats()) {
				if (!seat.isBooked() && seatsHeld.size() < numSeats) {
					System.out.println("Holding seat : " + seat.getSeatNbr());
					seatsHeld.add(seat.setBooked(true));
				} else if (seatsHeld.size() >= numSeats)
					break;
			}
			//seatHold.setSeatsHeld(seatsHeld);
			seatHold.setHoldingTime(Calendar.getInstance().getTimeInMillis());
			//System.out.println("Seats Held at : " + Calendar.getInstance().getTimeInMillis());
			this.venue.getSeatHolds().put(seatHoldId, seatHold);
		}
		System.out.println("Hold Identifier : " + seatHoldId);
		return seatHold;
	}

}
