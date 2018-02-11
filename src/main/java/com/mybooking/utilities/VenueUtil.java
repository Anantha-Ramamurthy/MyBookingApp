package com.mybooking.utilities;

import java.util.UUID;

import com.mybooking.entities.Seat;
import com.mybooking.entities.SeatHold;
import com.mybooking.entities.SeatReserve;
import com.mybooking.entities.Venue;

public class VenueUtil {

	public static final String INVALID_INPUT = "Please check the input values provided.";
	public static final String UNAVAILABLE = "Not enough seats available to fulfill the request.";
	public static final String INVALID_HOLD_ID = "The Hold ID provided is Invalid.";
	public static final String EMAIL_ID_NOT_VALID_FOR_HOLD = "The Email Id is not valid for the Hold Id provided.";
	

	public static UUID getUUID() {
		return UUID.randomUUID();
	}

	public static int getTotalSeatsHeld(Venue venue) {
		int totalSeatsHeld = 0;
		if (venue.getSeatHolds().keySet() == null)
			return 0;
		for (String seatHoldId : venue.getSeatHolds().keySet()) {
			totalSeatsHeld += venue.getSeatHolds().get(seatHoldId).getNoOfSeatsHeld();
		}
		return totalSeatsHeld;
	}

	public static int getTotalSeatsReserved(Venue venue) {
		int totalSeatsReserved = 0;
		if (venue.getSeatReserved().keySet() == null)
			return 0;
		for (String reservationId : venue.getSeatReserved().keySet()) {
			totalSeatsReserved += venue.getSeatReserved().get(reservationId).getNoOfSeatsBooked();
		}
		return totalSeatsReserved;
	}
	
	public static SeatReserve holdToReserved(SeatHold seatHold) {
		if (seatHold == null) return null;
		SeatReserve seatReserve = new SeatReserve(seatHold.getSeatHoldId(), seatHold.getCustomerEmail(), seatHold.getNoOfSeatsHeld());
		for (Seat seat : seatHold.getSeatsHeld()) {
			seatReserve.getSeatsBooked().add(seat);
			// TODO change the seat indicator here
		}
		return seatReserve;
	}
	
	public static boolean isEmpty(String val) {
		return (val == null || val.trim().length() == 0) ? true : false;
	}
}
