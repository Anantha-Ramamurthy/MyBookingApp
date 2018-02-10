package com.mybooking.myapp;

import com.mybooking.entities.SeatHold;
import com.mybooking.entities.Venue;
import com.mybooking.utilities.VenueUtil;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for TicketService.
 */
public class TicketServiceTest 
    extends TestCase
{
	Venue venue = Venue.getInstance();
	TicketService ts = new TicketServiceImpl(venue);
	String customer1 = "a@a.com";
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TicketServiceTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TicketServiceTest.class );
    }


    public void testFindAndHoldSeats()
    {
    		SeatHold sh = ts.findAndHoldSeats(5, customer1);
    		assertNotNull("Shipping Hold Object Null.", sh);
        assertTrue("No. of tickets held not the same as requested", 5 == VenueUtil.getTotalSeatsHeld(venue));
        assertTrue("Email on SeatHold Object did not match the customer email", customer1.equals(sh.getCustomerEmail()));
    }
    
    public void testNumSeatsAvailable() {
    		venue.reset();
    	    assertTrue("NumSeatsAvailable() not returning the right value.", ts.numSeatsAvailable() == venue.getSeats().size());
    		SeatHold sh = ts.findAndHoldSeats(9, customer1);
    		assertTrue("NumSeatsAvailable() not returning the right value after hold", ts.numSeatsAvailable() == (venue.getSeats().size()-9));
    }
}
