/**
 * 
 */
package com.mybooking.myapp;

import java.util.Calendar;
import com.mybooking.entities.Seat;
import com.mybooking.entities.SeatHold;
import com.mybooking.entities.Venue;

/**
 * @author anantha.ramamurthy
 *
 */
public class EvictorThread implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// this thread runs every 10 secs and cleans up SeatHolds that were not
		// confirmed. Has to be synchronized for accessing the Venue
		Venue venue = Venue.getInstance();
		System.out.println("Evictor thread Started !!! " + Thread.currentThread().getName());

		while (true) {
			System.out.println("Venue (Before Eviction) : " + venue);
			long releaseHoldBefore = Calendar.getInstance().getTimeInMillis() - 10000;

			synchronized (venue) {
				for (String seatHeldId : venue.getSeatHolds().keySet()) {
					SeatHold sh = venue.getSeatHolds().get(seatHeldId);
					if (sh.getHoldingTime() < releaseHoldBefore) {
						for (Seat seat : sh.getSeatsHeld()) {
							System.out.println("Releasing hold on seat: " + seat.getSeatNbr());
							seat.resetBooking();
						}
						// remove the setHold object from the map.
						venue.getSeatHolds().remove(seatHeldId);
					}

				}
				System.out.println("Venue (After Eviction) : " + venue);
			}
			System.out.println("Evictor completed run; Sleeping until next run! " + Thread.currentThread().getName());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				System.out.println("Evictor Thread interrupted; Needs to stop !!!");
				break;
			}
		}
	}

}
