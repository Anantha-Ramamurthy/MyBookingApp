package com.mybooking.utilities;

import org.apache.log4j.Logger;

/**
 * Holds the commonly used constants for the booking application.
 * <BR><U>NOTE</U>
 * <BR>The capacity of the Venue, hold expiration time and the thread cycle tiime are all loaded
 * <BR>from the system environment if found, or defaulted to preset values.
 * 
 * @author anantha.ramamurthy
 *
 */
public class BookingAppConstants {

	public final static Logger logger = Logger.getLogger("BookingApp");
	
	public final static int capacity = Integer.valueOf(System.getProperty("venue.capacity", "50")).intValue();
	public final static long evictorFrequency = Integer.valueOf(System.getProperty("evictor.cycleTime", "20000")).intValue();
	public final static long seatExpirationTime = Integer.valueOf(System.getProperty("seat.expirationTime", "20000")).intValue();
	
	public final static String BOOKING_ID_LOG = "Booking Identifier : ";
	public final static String VENUE_SEAT_STATUS_LOG = "Venue Seat Status : ";
	public final static String HOLD_ID_LOG = "Hold Identifier : ";
	public final static String HOLDING_SEAT_LOG ="Holding seat : ";
	public final static String RESERVED_SEAT_LOG ="Reserved seat : ";
	
	public static final String INVALID_INPUT = "Please check the input values provided.";
	public static final String UNAVAILABLE = "Not enough seats available to fulfill the request.";
	public static final String INVALID_HOLD_ID = "The Hold ID provided is Invalid.";
	public static final String EMAIL_ID_NOT_VALID_FOR_HOLD = "The Email Id is not valid for the Hold Id provided.";
	public final static String ERROR = "ERROR";
	public static final String LINE_STR = "-----------------------------------------------------------------------------------------------";
	

}
