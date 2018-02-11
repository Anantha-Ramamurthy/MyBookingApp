package com.mybooking.utilities;

import java.util.UUID;

import com.mybooking.entities.Seat;
import com.mybooking.entities.SeatHold;
import com.mybooking.entities.SeatReserve;
import com.mybooking.entities.Venue;
import org.apache.log4j.Logger;

/**
 * Utility class that helps with the most common operations on the Venue.
 * 
 * @author anantha.ramamurthy
 *
 */
public class VenueUtil {

	final static Logger logger = BookingAppConstants.logger;

	public static UUID getUUID() {
		return UUID.randomUUID();
	}

	/**
	 * Returns the total number of seats held at the venue, at any point in time.
	 * 
	 * @param venue 
	 * 		Venue Object
	 * @return total number of seats put on Hold at the venue 
	 */
	public static int getTotalSeatsHeld(Venue venue) {
		int totalSeatsHeld = 0;
		if (venue.getSeatHolds().keySet() == null)
			return 0;
		for (String seatHoldId : venue.getSeatHolds().keySet()) {
			totalSeatsHeld += venue.getSeatHolds().get(seatHoldId).getNoOfSeatsHeld();
		}
		return totalSeatsHeld;
	}

	/**
	 * Returns the total number of seats confirmed/booked by customers at the venue, at any point in time.
	 * 
	 * @param venue 
	 * 		Venue Object
	 * @return total number of seats that have been booked and confirmed.
	 */
	public static int getTotalSeatsReserved(Venue venue) {
		int totalSeatsReserved = 0;
		if (venue.getSeatReserved().keySet() == null)
			return 0;
		for (String reservationId : venue.getSeatReserved().keySet()) {
			totalSeatsReserved += venue.getSeatReserved().get(reservationId).getNoOfSeatsBooked();
		}
		return totalSeatsReserved;
	}

	/**
	 * Converts a SeatHold object to a SeatReserve Object, by flipping the status of the seat(s)
	 * from Hold to Reserved.
	 * 
	 * @param seatHold The SeatHold object that needs to be transformed.
	 * @return a SeatReserve object with the reserved seats and relevant information.
	 */
	public static SeatReserve holdToReserved(SeatHold seatHold) {
		if (seatHold == null)
			return null;
		SeatReserve seatReserve = new SeatReserve(seatHold.getSeatHoldId(), seatHold.getCustomerEmail(),
				seatHold.getNoOfSeatsHeld());
		for (Seat seat : seatHold.getSeatsHeld()) {
			seatReserve.getSeatsBooked().add(seat.setBooked(true, 2));
			logger.debug(BookingAppConstants.RESERVED_SEAT_LOG + seat.getSeatNbr());
		}
		return seatReserve;
	}

	/**
	 * Checks if the presented reservationId is valid for the venue.
	 * 
	 * @param reervationId
	 * 		The reservaton ID to be checked
	 * @param venue 
	 * 		Venue Object
	 * @return true 
	 * 		If reservation found, else false
	 */
	public static boolean checkReservation(String reervationId, Venue venue) {
		if (isEmpty(reervationId) || venue == null) return false;
		return venue.getSeatReserved().containsKey(reervationId);
	}
	
	/**
	 * Checks if the presented holdId is valid for the venue.
	 * 
	 * @param holdId
	 * 			The hold ID to be checked
	 * @param venue 
	 * 		Venue Object
	 * @return true 
	 * 		If seat hold found for holdId, else false
	 */
	public static boolean checkHold(String holdId, Venue venue) {
		if (isEmpty(holdId) || venue == null) return false;
		return venue.getSeatHolds().containsKey(holdId);
	}
	
	/**
	 * Utility function to check if a string is empty. Handles Null values.
	 * 
	 * @param val 
	 * 			The string to be checked.
	 * @return
	 * 		False if null or empty
	 */
	public static boolean isEmpty(String val) {
		return (val == null || val.trim().length() == 0) ? true : false;
	}
}
