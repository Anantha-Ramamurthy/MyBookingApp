package com.mybooking.myapp;

import com.mybooking.entities.SeatHold;
import com.mybooking.entities.Venue;

/**
 * Hello world!
 *
 */
public class BookingApp {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		Venue venue = Venue.getInstance();
		TicketService ts = new TicketServiceImpl(venue);
		SeatHold sh = ts.findAndHoldSeats(3, "a@a.com");
		System.out.println(sh.getSeatHoldId());
		System.out.println(venue);
	}
}
