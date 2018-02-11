package com.mybooking.entities;

/**
 * Represents the Seat available at the venue; A seat is identified by a Seat
 * Number, booking flag and the booking type (1 - Hold, 2 - Reserved) if it has been reserved.
 * 
 * @author anantha.ramamurthy
 *
 */
public class Seat {
	private int seatNbr;
	private boolean booked = false;
	private int bookingType; // 1 - Hold, 2 - Reserved

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

	public Seat setBooked(boolean isBooked, int type) {
		this.booked = isBooked;
		this.bookingType = type;
		return this;
	}

	public int getBookingType() {
		return bookingType;
	}

	public void setBookingType(int bookingType) {
		this.bookingType = bookingType;
	}
	
	public void resetBooking() {
		this.booked = false;
		this.bookingType = 0;
	}

	public String toString() {
		if (booked) 
			return "[" + String.valueOf(this.seatNbr) + ":" + (this.bookingType == 1?"H":"R") + "]";
		else
			return "[" + String.valueOf(this.seatNbr) + ":" + " ]";
	}
}
