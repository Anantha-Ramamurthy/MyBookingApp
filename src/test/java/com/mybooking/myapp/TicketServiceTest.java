package com.mybooking.myapp;

import com.mybooking.entities.SeatHold;
import com.mybooking.entities.Venue;
import com.mybooking.utilities.BookingAppConstants;
import com.mybooking.utilities.VenueUtil;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for TicketService.
 */
public class TicketServiceTest extends TestCase {
	Venue venue;
	TicketService ts;
	String customerEmail_1, customerEmail_2, customerEmail_3;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public TicketServiceTest(String testName) {
		super(testName);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		venue = Venue.getInstance();
		ts = new TicketServiceImpl(venue);
		customerEmail_1 = "a@a.com";
		customerEmail_2 = "b@b.com";
		customerEmail_3 = "c@c.om";
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		venue = null;
		ts = null;
		customerEmail_1 = null;
		customerEmail_2 = null;
		customerEmail_3 = null;
	}	

	/**
	 * Unit test for method FindAndHoldSeats()
	 * This test finds and holds a set of seats. 
	 * Validates the following:
	 *     SeatHoldObject being returned
	 *     No. Of tickets reported as being held
	 *     The customer email in the returned SeatHold object
	 */
	public void testFindAndHoldSeats() {
		venue.init();
		SeatHold sh = ts.findAndHoldSeats(5, customerEmail_1);
		assertNotNull("Seat Hold Object Null.", sh);
		assertTrue("No. of tickets held not the same as requested.", 5 == VenueUtil.getTotalSeatsHeld(venue));
		assertTrue("Email on SeatHold Object did not match the customer email.", customerEmail_1.equalsIgnoreCase(sh.getCustomerEmail()));
	}

	/**
	 * Unit test for method ReserveSeats()
	 * This test reserves a set of seats. 
	 * Validates the following:
	 *     Correct bookingId being returned.
	 *     Reservation Exists in the system.
	 */
	public void testReserveSeats() {
		venue.init();
		SeatHold sh = ts.findAndHoldSeats(7, customerEmail_1);
		String reervationId = ts.reserveSeats(sh.getSeatHoldId(), customerEmail_1);
		assertNotNull("Reservation Id not returned.", reervationId);
		assertEquals("Invalid Reservation Id returned.", reervationId, sh.getSeatHoldId());
		assertTrue("Reservation doesnot exist in the system.", VenueUtil.checkReservation(reervationId, venue));
	}

	
	/**
	 * Unit test for method NumSeatsAvailable()
	 * This test checks if the method returns the correct no. of seats in various scenarios. 
	 * Validates the following:
	 *     No. of seats at the start
	 *     No. of seats after a successful hold
	 *     No. of seats after a successful reservation
	 */
	public void testNumSeatsAvailable() {
		venue.init();
		assertTrue("Invalid No. of seats at the start.",
							ts.numSeatsAvailable() == venue.getSeats().size());
		SeatHold sh = ts.findAndHoldSeats(9, customerEmail_1);
		assertTrue("Invalid No. of seats after seat hold.",
							ts.numSeatsAvailable() == (venue.getSeats().size() - 9));
		ts.reserveSeats(sh.getSeatHoldId(), customerEmail_1);
		assertTrue("Invalid No. of seats after seat reservation.",
							ts.numSeatsAvailable() == (venue.getSeats().size() - 9));
	}

}
