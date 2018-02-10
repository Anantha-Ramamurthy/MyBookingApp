
package com.mybooking.entities;

import java.util.*;

/**
 * This class models the information on the Seats that have been booked for a
 * customer;
 * 
 * @author anantha.ramamurthy
 *
 */
public class SeatReserve {
	private String reservationId;
	private String customerEmail;
	private int noOfSeatsBooked;
	private Map<String, List<Seat>> seatsBooked;

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public int getNoOfSeatsBooked() {
		return noOfSeatsBooked;
	}

	public void setNoOfSeatsBooked(int noOfSeatsBooked) {
		this.noOfSeatsBooked = noOfSeatsBooked;
	}

	public Map<String, List<Seat>> getSeatsBooked() {
		return seatsBooked;
	}
}
