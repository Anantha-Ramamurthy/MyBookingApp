package com.mybooking.entities;

import java.util.*;

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
	private Map<String, SeatReserve> seatReserved;
	private List<Seat> seats;

	private Venue() {
		if (instance != null) {
			throw new IllegalStateException("Singleton already in place");
		}
		reset();
	}

	public void reset() {
		this.seats = new ArrayList<Seat>();
		for (int i = 1; i <= capacity; i++) {
			this.seats.add(new Seat(i));
		}
		this.seatHolds = new HashMap<String, SeatHold>(); // TODO Use concur version for better performance
		this.seatReserved = new HashMap<String, SeatReserve>();
	}

	// getInstance should be used to instantiate the singleton
	public static Venue getInstance() {
		return instance;
	}
	/*
	 * public void loadVenueOld(int rows, int seatsPerRow) { this.seats = new
	 * ArrayList<List<Seat>>(); for (int i=0; i<rows; i++) { List<Seat> seatsInARow
	 * = new ArrayList<Seat>(); for (int j=0; i<seatsPerRow; j++) {
	 * seatsInARow.add(new Seat(j)); } seats.add(seatsInARow); } }
	 */

	/*
	 * public void loadVenue(int rows, int seatsPerRow) { int startRowChar =
	 * Character.getNumericValue('A'); this.seatMap = new HashMap<String,
	 * List<Seat>>(); for (int i=0; i<rows; i++) { List<Seat> seatsInARow = new
	 * ArrayList<Seat>(); for (int j=0; i<seatsPerRow; j++) { seatsInARow.add(new
	 * Seat(j)); } seatMap.put(String.valueOf(startRowChar+i), seatsInARow); }
	 * this.capacity = rows*seatsPerRow; }
	 */

	public Map<String, SeatReserve> getSeatReserved() {
		return seatReserved;
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
		this.seatReserved = seatReserved;
	}

	/*
	 * public Map<String, List<Seat>> getSeatMap() { return seatMap; }
	 * 
	 * public void setSeatMap(Map<String, List<Seat>> seatMap) { this.seatMap =
	 * seatMap; }
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Seat seat : this.getSeats()) {
			sb.append(seat.toString());
		}
		return sb.toString();
	}
}
