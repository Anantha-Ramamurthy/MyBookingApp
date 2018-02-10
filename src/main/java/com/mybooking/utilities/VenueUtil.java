package com.mybooking.utilities;

import java.util.UUID;

import com.mybooking.entities.Venue;

public class VenueUtil {

	public static String UNAVAILABLE = "Not enough seats available to fulfill the request";

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
}
