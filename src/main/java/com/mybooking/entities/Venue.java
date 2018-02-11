package com.mybooking.entities;

import java.util.*;

import com.mybooking.myapp.EvictorThread;

/**
 * Singleton representation of the Venue. This is the simplest implementation
 * of the Singleton pattern without doing double checked locking etc. The class is
 * instantiated at load time.
 * 
 * @author anantha.ramamurthy
 *
 */
public class Venue {

	private static Venue instance = new Venue();
	private int capacity = 10;
	private Map<String, SeatHold> seatHolds;
	private Map<String, SeatReserve> seatsReserved;
	private List<Seat> seats;

	private Venue() {
		if (instance != null) {
			throw new IllegalStateException("Singleton already in place");
		}
		reset();
		// Start the Hold Evictor thread.
		new Thread(new EvictorThread()).start();
	}

	public void reset() {
		this.seats = new ArrayList<Seat>();
		for (int i = 1; i <= capacity; i++) {
			this.seats.add(new Seat(i));
		}
		this.seatHolds = new HashMap<String, SeatHold>(); // TODO Use concur version for better performance
		this.seatsReserved = new HashMap<String, SeatReserve>();
		System.out.println("Venue Initialized with capacity : " + capacity);
	}

	// getInstance should be used to instantiate the singleton
	public static Venue getInstance() {
		return instance;
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

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Seat seat : this.getSeats()) {
			sb.append(seat.toString());
		}
		return sb.toString();
	}
}
