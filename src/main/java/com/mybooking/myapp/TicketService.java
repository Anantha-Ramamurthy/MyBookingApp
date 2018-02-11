/**
 * 
 */
package com.mybooking.myapp;

import com.mybooking.entities.SeatHold;

/**
 * This interface exposes the commonly supported operations of the ticketing
 * service.
 * 
 * @author anantha.ramamurthy
 *
 */
public interface TicketService {

	/**
	 * The number of seats in the venue that are neither held nor reserved
	 *
	 * @return the number of tickets available for booking at any point in time at the venue
	 */
	int numSeatsAvailable();

	/**
	 * Find and hold the best available seats for a customer; The best available
	 * seats are considered the seats closer to the stage.
	 *
	 * @param numSeats
	 *            the number of seats to find and hold
	 * @param customerEmail
	 *            unique identifier for the customer
	 * @return a SeatHold object identifying the specific seats against a unique id.
	 */
	SeatHold findAndHoldSeats(int numSeats, String customerEmail);

	/**
	 * Commit and Confirm seats held for a specific customer
	 *
	 * @param seatHoldId
	 *            the seat hold identifier
	 * @param customerEmail
	 *            the email address of the customer to which the seat hold is
	 *            assigned
	 * @return a reservation confirmation code (the same as the Hold ID if successful; else ERROR)
	 */
	String reserveSeats(String seatHoldId, String customerEmail);

}
