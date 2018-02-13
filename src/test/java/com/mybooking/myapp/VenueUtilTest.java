package com.mybooking.myapp;

import com.mybooking.entities.SeatHold;
import com.mybooking.entities.SeatReserve;
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
public class VenueUtilTest extends TestCase {
	Venue venue;
	TicketService ts;
	String customerEmail_1, customerEmail_2, customerEmail_3;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public VenueUtilTest(String testName) {
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
	 * Unit test for method GetTotalSeatsHeld()
	 * This test finds and holds a set of seats and validates if 
	 * 		the total number of seats went up by the no. of seats put on Hold.
	 */
	public void testGetTotalSeatsHeld() {
		venue.init();
		int beforeHold = VenueUtil.getTotalSeatsHeld(venue);
		SeatHold sh = ts.findAndHoldSeats(5, customerEmail_1);
		int afterHold = VenueUtil.getTotalSeatsHeld(venue);
		assertNotNull("Invalid total number of seats returned after hold.", 5 == (afterHold-beforeHold));
	}

	/**
	 * Unit test for method GetTotalSeatsReserved()
	 * This test reserves a set of seats and tests if  
	 *     The total number of seats reserved went up by the no. of seats reserved.
	 */
	public void testGetTotalSeatsReserved() {
		venue.init();
		int beforeReserve = VenueUtil.getTotalSeatsReserved(venue);
		SeatHold sh = ts.findAndHoldSeats(7, customerEmail_2);
		ts.reserveSeats(sh.getSeatHoldId(), customerEmail_2);
		int afterReserve = VenueUtil.getTotalSeatsReserved(venue);
		assertTrue("Invalid total number of seats returned after reserve.",
				7 == (afterReserve-beforeReserve));
	}

	
	/**
	 * Unit test for method holdToReserved()
	 * This test checks if the method converts the SeatHold to SeatReserve with the right values. 
	 * Validates the following:
	 *     Check for null inputs
	 *     Checks if the returned SeatReserve has the correct bookingID
	 *     Checks if the returned SeatReserve has the correct number of seats reserved
	 *     checks if the customer email id on Seat Reserve is correct
	 */
	public void testHoldToReserved() {
		venue.init();
		assertTrue("Invalid No. of seats at the start.",
							ts.numSeatsAvailable() == venue.getSeats().size());

		assertTrue("Invalid No. of seats after seat hold.",
							VenueUtil.holdToReserved (null) == null);
		SeatHold sh = ts.findAndHoldSeats(9, customerEmail_3);
		SeatReserve sr = VenueUtil.holdToReserved (sh);
		assertTrue("Invalid No. of seats after moving seat hold to reserved.",
				sr.getNoOfSeatsBooked() == sh.getNoOfSeatsHeld());
		assertTrue("Invalid email id on the seat reserved.",
							sr.getCustomerEmail() == sh.getCustomerEmail());
		assertTrue("Invalid booking Id moving from hold to reserved.",
				sr.getReservationId() == sh.getSeatHoldId());
	}
	
	/**
	 * Unit test for method checkHold()
	 * This test check for a Hold being present and validates for the following scenarios
	 *     the hold Id or Venue being sent as Null.
	 *     the hold Id being sent as an Empty String.
	 *     venue sent as Null
	 *     Invalid non existent hold id
	 *     Valid Hold Id.
	 */
	public void testCheckHold() {
		venue.init();
		assertTrue("Invalid result on checkHold.", false == VenueUtil.checkHold(null, venue));
		assertTrue("Invalid result on checkHold.", false == VenueUtil.checkHold("", null));
		assertTrue("Invalid result on checkHold.", false == VenueUtil.checkHold("testId", null));
		assertTrue("Invalid result on checkHold.", false == VenueUtil.checkHold(null, null));
		assertTrue("Invalid result on checkHold.", false == VenueUtil.checkHold("testId", venue));
		
		SeatHold sh = ts.findAndHoldSeats(7, customerEmail_2);
		assertTrue("Invalid result on checkHold.", true == VenueUtil.checkHold(sh.getSeatHoldId(), venue));
	}
	
	/**
	 * Unit test for method checkReservation()
	 * This test check for a reservation being present and validates for the following scenarios
	 *     the reservation Id or Venue being sent as Null.
	 *     the reservation Id being sent as an Empty String.
	 *     Invalid non existent reservation id
	 *     Valid reservation Id.
	 */
	public void testCheckReservation() {
		venue.init();
		assertTrue("Invalid result on checkReservation.", false == VenueUtil.checkReservation(null, venue));
		assertTrue("Invalid result on checkReservation.", false == VenueUtil.checkReservation("", null));
		assertTrue("Invalid result on checkReservation.", false == VenueUtil.checkReservation("testId", null));
		assertTrue("Invalid result on checkReservation.", false == VenueUtil.checkReservation(null, null));
		assertTrue("Invalid result on checkReservation.", false == VenueUtil.checkReservation("testId", venue));
		
		SeatHold sh = ts.findAndHoldSeats(7, customerEmail_2);
		String srId = ts.reserveSeats(sh.getSeatHoldId(), customerEmail_2);
		assertTrue("Invalid result on checkReservation.", true == VenueUtil.checkReservation(srId, venue));
	}
	
	/** 
	 * Unit test for the isEmpty()
	 * Checks the outcome for various values of the String input
	 */
	public void testIsEmpty() {
		assertTrue("Invalid outcome for String input.", true == VenueUtil.isEmpty(null));
		assertTrue("Invalid outcome for String input.", true == VenueUtil.isEmpty(""));
		assertTrue("Invalid outcome for String input.", true == VenueUtil.isEmpty("   "));
		assertTrue("Invalid outcome for String input.", false == VenueUtil.isEmpty("String"));
	}

}
