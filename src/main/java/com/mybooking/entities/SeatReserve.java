
package com.mybooking.entities;

import java.util.*;

/**
 * This class models the information on the Seats that have been booked and confirmed for a customer.
 * <br>The booking is identified by a unique Reservation Identifier.
 * <br>The customer is identified using an email id.
 * 
 * @author anantha.ramamurthy
 *
 */
public class SeatReserve {
	private String reservationId;
	private String customerEmail;
	private int noOfSeatsBooked;
	private List<Seat> seatsBooked;

	public SeatReserve(String reservationId, String customerEmail, int noOfSeatsBooked) {
		super();
		this.reservationId = reservationId;
		this.customerEmail = customerEmail;
		this.noOfSeatsBooked = noOfSeatsBooked;
		this.seatsBooked = new ArrayList<Seat>();
	}

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

	public List<Seat> getSeatsBooked() {
		return seatsBooked;
	}
}
