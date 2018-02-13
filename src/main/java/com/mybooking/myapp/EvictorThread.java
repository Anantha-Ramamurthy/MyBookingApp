/**
 * 
 */
package com.mybooking.myapp;

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.mybooking.entities.Seat;
import com.mybooking.entities.SeatHold;
import com.mybooking.entities.Venue;
import com.mybooking.utilities.BookingAppConstants;
import com.mybooking.utilities.VenueUtil;

/**
 * Implementation of a thread that would run in parallel to the Ticket Service, to clean up seats that were not confirmed
 * <br> within a certain time limit(expired holds). The thread goes into a sleep state and returns again after the set time (frequency)
 * <br> to clean up any expired seat holds.
 * <br>
 * <br>NOTE: This implementation sets the expiration time for a hold and the thread cycle time to be the same, for effecient clean up in every run.
 * <br>The thread frequency can be different than the expiration time, to run it in less or more frequent intervals.
 * <br>
 * 
 * @author anantha.ramamurthy
 *
 */
public class EvictorThread implements Runnable {

	private long runFrequency;
	final Logger logger = BookingAppConstants.logger;

	public EvictorThread(long millisecs) {
		this.runFrequency = millisecs;
	}

	/*
	 * (non-Javadoc)
	 * Implementation for the thread function.
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		Venue venue = Venue.getInstance();
		logger.info("Evictor thread Started!!! Thread Id is : " + Thread.currentThread().getName());

		while (true) {
			logger.debug("Venue (Before Eviction) : " + venue);
			long releaseHoldBefore = Calendar.getInstance().getTimeInMillis() - BookingAppConstants.seatExpirationTime;

			synchronized (venue) {
				for (String seatHeldId : venue.getSeatHolds().keySet()) {
					SeatHold sh = venue.getSeatHolds().get(seatHeldId);
					if (sh.getHoldingTime() < releaseHoldBefore) {
						for (Seat seat : sh.getSeatsHeld()) {
							logger.debug("Releasing hold on seat: " + seat.getSeatNbr());
							seat.resetBooking();
						}
						// remove the seetHold object after eviction.
						venue.getSeatHolds().remove(seatHeldId);
					}

				}
				logger.debug("Venue (After Eviction) : " + venue);
			}
			logger.info("Evictor completed run; Sleeping until next run!");
			try {
				Thread.sleep(runFrequency);
			} catch (InterruptedException ex) {
				logger.error("Evictor Thread interrupted; Needs to stop!!!");
				break;
			}
		}
	}

}
