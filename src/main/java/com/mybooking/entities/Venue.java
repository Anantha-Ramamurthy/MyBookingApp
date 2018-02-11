package com.mybooking.entities;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.mybooking.myapp.EvictorThread;
import com.mybooking.utilities.BookingAppConstants;

import org.apache.log4j.Logger;

/**
 * Singleton representation of the Venue. Simplest implementation of
 * the Singleton pattern by instantiating the instance when the class is loaded.
 * <br>
 * <br>The Venue is initialized to a certain capacity of seats (configurable by a system property).
 * <br>The seats are numbered from 1 to N, where 1 is the closest to the Stage and N is the farthest.
 * <br>The Best Seats for viewing are considered to be the ones closer to the stage.
 * <br>Internally maintains a map of the SeatHeld and SeatsReserved against their Booking Id.
 * <br>
 * <br>NOTE: When the Venue is initialized, it also starts a Seat Evictor thread, to clean up
 * bookings that were held for a certain time and were not confirmed (Expired bookings).
 * 
 * 
 * @author anantha.ramamurthy
 *
 */
public class Venue {

	private static Venue instance = new Venue();
	private int capacity = BookingAppConstants.capacity;
	private Map<String, SeatHold> seatHolds;
	private Map<String, SeatReserve> seatsReserved;
	private List<Seat> seats;
	private Thread ticketEvictor;
	final Logger logger = BookingAppConstants.logger;

	private Venue() {
		if (instance != null) {
			throw new IllegalStateException("Singleton already in place");
		}
		init();
	}

	/**
	 * Initializes the venue with the seat capacity, and resets the booking and seat holds on it.
	 */
	public synchronized void init() {
		this.seats = new ArrayList<Seat>();
		for (int i = 1; i <= capacity; i++) {
			this.seats.add(new Seat(i));
		}
		this.seatHolds = new ConcurrentHashMap<String, SeatHold>();
		this.seatsReserved = new ConcurrentHashMap<String, SeatReserve>();
		logger.info("Venue Initialized with capacity : " + capacity);
		// Start the Hold Evictor in a new thread.
		if (ticketEvictor == null || !ticketEvictor.isAlive()) {
		ticketEvictor = new Thread(new EvictorThread(BookingAppConstants.evictorFrequency));
		ticketEvictor.setDaemon(true); // For stopping the thread when the main user thread exits.
		ticketEvictor.start();
		}
	}


	/**
	 * Returns a single instance of the Venue duly initialized.
	 * @return a singleton instance of Venu
	 */
	public static Venue getInstance() {
		return instance;
	}

	/**
	 * Returns the handle to the Evictor Thread, for checking its status etc
	 * @return Handle to the Seat Eviction Thread
	 */
	public Thread getTicketEvictor() {
		return ticketEvictor;
	}

	public Map<String, SeatReserve> getSeatReserved() {
		return seatsReserved;
	}

	public Map<String, SeatHold> getSeatHolds() {
		return seatHolds;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<Seat> getSeats() {
		return this.seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public void setSeatHolds(Map<String, SeatHold> seatHolds) {
		this.seatHolds = seatHolds;
	}

	public void setSeatReserved(Map<String, SeatReserve> seatReserved) {
		this.seatsReserved = seatReserved;
	}

	/**
	 * Returns a String representation of the Venue, with the booking and hold status.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Seat seat : this.getSeats()) {
			sb.append(seat.toString());
		}
		return sb.toString();
	}
}
