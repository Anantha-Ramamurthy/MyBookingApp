package com.mybooking.myapp;

import com.mybooking.entities.SeatHold;
import com.mybooking.entities.Venue;

/**
 * Hello world!
 *
 */
public class BookingApp {
	public static void main(String[] args) {
		Venue venue = Venue.getInstance();
		TicketService ts = new TicketServiceImpl(venue);
		System.out.println("Ticket Service started !!!");
		
		
		SeatHold sh = ts.findAndHoldSeats(3, "a@a.com");
		System.out.println("Venue (After Hold) : "+venue);
	}
}
