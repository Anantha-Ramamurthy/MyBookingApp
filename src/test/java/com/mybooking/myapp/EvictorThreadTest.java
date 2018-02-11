/**
 * 
 */
package com.mybooking.myapp;

import com.mybooking.entities.SeatHold;
import com.mybooking.entities.Venue;
import com.mybooking.utilities.BookingAppConstants;
import com.mybooking.utilities.VenueUtil;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

/**
 * @author ananth
 *
 */
public class EvictorThreadTest extends TestCase {
	Venue venue;
	TicketService ts;

	/**
	 * @param name
	 */
	public EvictorThreadTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		venue = Venue.getInstance();
		ts = new TicketServiceImpl(venue);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		venue = null;
		ts = null;
	}

	/**
	 * Unit test for method run()
	 * This test checks if the Evictor Thread has started and is alive. 
	 */
	public void testEvictorThread() {
		assertTrue("Seat Evictor Thread not alive on the Venue.", Venue.getInstance().getTicketEvictor().isAlive());
	}
	
	/**
	 * Unit test for testing the clean up of Holds that Expired.
	 * Holds a set of seats and goes into a sleep, to let the seats expire.
	 * Checks if the seats have been claimed by the Eviction process by doing a reservation
	 * against the Expired Hold Id
	 */
	public void testEvictionProcess() {
		venue.init();
		try {
			 SeatHold sh = ts.findAndHoldSeats(3, "a@a.com");
			 
			 // Let the hold EXPIRE
			 Thread.sleep(BookingAppConstants.evictorFrequency*2); 
			 // Try Confirming the seats after expiration time.
			 
			 String reservationId = ts.reserveSeats(sh.getSeatHoldId(), "a@a.com");
			 assertTrue("Evictor did not evict seats held.", reservationId.equalsIgnoreCase("ERROR"));
		} catch (InterruptedException ex) {
			throw new AssertionFailedError("Unexpected Error.");
		}
	}

}
