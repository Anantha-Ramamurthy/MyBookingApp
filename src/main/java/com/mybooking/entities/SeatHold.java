
package com.mybooking.entities;

import java.util.*;

/**
 * This class models the information on the Seats Being Held for a customer;
 * 
 * @author anantha.ramamurthy
 *
 */
public class SeatHold {
	private String seatHoldId;
	private String customerEmail;
	private int noOfSeatsHeld;
	private List<Seat> seatsHeld;
	private long holdingTime;

	public SeatHold(String seatHoldId, String customerEmail, int noOfSeatsHeld) {
		super();
		this.seatHoldId = seatHoldId;
		this.customerEmail = customerEmail;
		this.noOfSeatsHeld = noOfSeatsHeld;
		this.seatsHeld = new ArrayList<Seat>();
	}

	public long getHoldingTime() {
		return holdingTime;
	}

	public void setHoldingTime(long holdingTime) {
		this.holdingTime = holdingTime;
	}

	public String getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(String seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public int getNoOfSeatsHeld() {
		return noOfSeatsHeld;
	}

	public void setNoOfSeatsHeld(int noOfSeatsHeld) {
		this.noOfSeatsHeld = noOfSeatsHeld;
	}

	public List<Seat> getSeatsHeld() {
		return seatsHeld;
	}

}
