package com.mybooking.myapp;

import com.mybooking.entities.SeatHold;
import com.mybooking.entities.Venue;

/**
 * Hello world!
 *
 */
public class BookingApp {
	public static void main(String[] args) throws Exception {
		Venue venue = Venue.getInstance();
		TicketService ts = new TicketServiceImpl(venue);
		System.out.println("Ticket Service started !!!");
		
     	SeatHold sh = ts.findAndHoldSeats(3, "a@a.com");
		System.out.println("Venue (After Hold) : "+venue);
		
		ts.reserveSeats(sh.getSeatHoldId(), "a@a.com");
		System.out.println("Venue (After Reservation) : "+venue);
		
		SeatHold sh1 = ts.findAndHoldSeats(3, "b@b.com");
		System.out.println("Venue (After Hold) : "+venue);
		
		// Sleep for 5 secs
		Thread.sleep(5000);
		
		SeatHold sh2 = ts.findAndHoldSeats(3, "c@c.com");
		System.out.println("Venue (After Hold) : "+venue);
		ts.reserveSeats(sh2.getSeatHoldId(), "c@c.com");
		System.out.println("Venue (After Reservation) : "+venue);
		
		// Sleep for 5 secs
		Thread.sleep(5000);
		
		// Try expired Hold Id
		ts.reserveSeats(sh1.getSeatHoldId(), "b@b.com");
		System.out.println("Venue (After Reservation) : "+venue);
		// Not Enough Seats to Hold
		SeatHold sh3 = ts.findAndHoldSeats(5, "d@d.com");
		System.out.println("Venue (After Hold) : "+venue);
		// Enough seats to Hold. Let them collected by the Evictor
		SeatHold sh4 = ts.findAndHoldSeats(4, "d@d.com");
		System.out.println("Venue (After Hold) : "+venue);
	}
}
