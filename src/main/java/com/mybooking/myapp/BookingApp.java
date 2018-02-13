package com.mybooking.myapp;

import java.util.Scanner;

import com.mybooking.entities.SeatHold;
import com.mybooking.entities.Venue;
import com.mybooking.utilities.BookingAppConstants;
import com.mybooking.utilities.VenueUtil;

/**
 * A SAMPLE ticket booking CLI client (<U>for testing the booking experience</U>)
 * <br>The customer would need an email to hold or confirm a booking.
 * <br> 
 * <br>To start the client, use the following in the application directory 
 * <br>$ mvn exec:java
 * <br>
 * <br>This emultaes a CLI client that can be used to book and hold seats by answering the prompts.
 * <br>
 * <br>Ex: Enter your choice (1: Hold, 2: Reserve, 3: Quit):
 * <br>
 * <br>
 */
public class BookingApp {
	
	/**
	 * Entry point to start testing the Booking Application through a CLI interface.
	 * @param args (None needed)
	 * @throws Exception in case of failures
	 */
	public static void main(String[] args) throws Exception {
		Venue venue = Venue.getInstance();
		TicketService ts = new TicketServiceImpl(venue);
		System.out.println();System.out.println();System.out.println();
		System.out.println("<<<<<     Venue Initialized with capacity : " + venue.getCapacity() + "     >>>>>");
		System.out.println(BookingAppConstants.LINE_STR);
		System.out.println("Ticket Service started !!!");
		System.out.println(BookingAppConstants.LINE_STR);

		Scanner scanner = new Scanner(System.in);
		boolean exitFlag = false;

		while (!exitFlag) {
			System.out.println(BookingAppConstants.LINE_STR);
			System.out.println();System.out.println();System.out.println();
			System.out.println("Enter your choice (1: Hold, 2: Reserve, 3: Print Venue Status 4. Quit): ");
			try {
				int noOfSeats;
				String customerEmail;
				String bookingID;

				switch (scanner.nextInt()) {
				case 1:
					System.out.println("No. of seats to hold? ");
					noOfSeats = scanner.nextInt();
					System.out.println("Customer Email Id? ");
					customerEmail = scanner.next();
					SeatHold seatHold = ts.findAndHoldSeats(noOfSeats, customerEmail);
					if (seatHold != null)
						System.out.println(
								"Seats successfully put on hold. Please use the following ID to confirm your booking : "
										+ seatHold.getSeatHoldId());
					else
						System.out.println("Error Occured!!! Requested number of seats not available. Please try again.");
					break;
				case 2:
					System.out.println("Booking Id? ");
					bookingID = scanner.next();
					System.out.println("Customer Email Id? ");
					customerEmail = scanner.next();
					if (bookingID.equalsIgnoreCase(ts.reserveSeats(bookingID, customerEmail))) {
						System.out.println("Seats successfully confirmed for booking : " + bookingID);
					} else
						System.out.println("Error Occured!!! The Seats held expired or Booking Id/Email Id could not be matched in the system.");
					break;
				case 3:
					System.out.println(BookingAppConstants.LINE_STR);
					System.out.println("<<<<<    Venue Booking Status (H-Hold, R-Reserved)   >>>>>");
					System.out.println(BookingAppConstants.LINE_STR);
					System.out.println(venue.toString());
					break;
				case 4:
					System.out.println("<<<<<THANK YOU FOR USING THE TICKET BOOKING SYSTEM>>>>> !!!");
					exitFlag = true;
					break;
				}
			} catch (Exception e) {
				// Log error and continue; No specific handling needed.
				System.out.println("Error Occured!!! Please try again.");
				scanner.next();
			}
		}
		// Close the scanner and release resources
		scanner.close();
	}
}
