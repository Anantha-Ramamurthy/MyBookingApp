package com.mybooking.entities;

/**
 * Represents the Seat available at the venue; A seat is identified by a Seat
 * Number, booking flag and the Booking ID if it has been reserved.
 * 
 * @author anantha.ramamurthy
 *
 */
public class Seat {
	private int seatNbr;
	// private int bookingID; // TODO DO we need this
	private boolean booked = false;

	public Seat(int seatNbr) {
		this.seatNbr = seatNbr;
	}

	public int getSeatNbr() {
		return seatNbr;
	}

	public void setSeatNbr(int seatNbr) {
		this.seatNbr = seatNbr;
	}

	public boolean isBooked() {
		return booked;
	}

	public Seat setBooked(boolean isBooked) {
		this.booked = isBooked;
		return this;
	}

/*	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}*/

	public void resetBooking()
	{
		this.booked = false;
	}
	
	public String toString() {
		return "[" + String.valueOf(this.seatNbr) + ":" + String.valueOf(this.booked) + "]";
	}
}
