/**
 * 
 */
package com.mybooking.myapp;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.mybooking.entities.Seat;
import com.mybooking.entities.SeatHold;
import com.mybooking.entities.SeatReserve;
import com.mybooking.entities.Venue;
import com.mybooking.utilities.BookingAppConstants;
import com.mybooking.utilities.VenueUtil;

/**
 * @author anantha.ramamurthy
 *
 */
public class TicketServiceImpl implements TicketService {

	Venue venue;
	final Logger logger = BookingAppConstants.logger;

	public TicketServiceImpl(Venue venue) {
		this.venue = venue;
	}

	/*
	 * (non-Javadoc)
	 * Gets the number of seats available for booking in the Venue, at any point in time.
	 * @see com.mybooking.myapp.TicketService#numSeatsAvailable()
	 */
	public int numSeatsAvailable() {
		return venue.getCapacity() - (VenueUtil.getTotalSeatsHeld(venue) + VenueUtil.getTotalSeatsReserved(venue));
	}


	/*
	 * (non-Javadoc)
	 * Implementation for holding the seats for a customer.
	 * @see com.mybooking.myapp.TicketService#findAndHoldSeats(int,
	 * java.lang.String)
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		/* Loop through the seats in the Venue and block the seats available.
		 The best seats are the ones closer to the stage. */
		
		String seatHoldId = VenueUtil.getUUID().toString();
		SeatHold seatHold = new SeatHold(seatHoldId, customerEmail, numSeats);
		List<Seat> seatsHeld = seatHold.getSeatsHeld();

		synchronized (this.venue) {
			if (numSeats <= 0 || numSeats > this.numSeatsAvailable()) {
				logger.error(BookingAppConstants.UNAVAILABLE);
				return null;
			}
			for (Seat seat : this.venue.getSeats()) {
				if (!seat.isBooked() && seatsHeld.size() < numSeats) {
					logger.debug(BookingAppConstants.HOLDING_SEAT_LOG + seat.getSeatNbr());
					seatsHeld.add(seat.setBooked(true, 1));
				} else if (seatsHeld.size() >= numSeats)
					break;
			}
			seatHold.setHoldingTime(Calendar.getInstance().getTimeInMillis());
			this.venue.getSeatHolds().put(seatHoldId, seatHold);
		}
		logger.info(BookingAppConstants.HOLD_ID_LOG + seatHoldId);
		logger.debug(BookingAppConstants.VENUE_SEAT_STATUS_LOG + venue);
		return seatHold;
	}

	
	/*
	 * (non-Javadoc)
	 * Implementation for reserving the seats that were held before, if unexpired.
	 * @see com.mybooking.myapp.TicketService#reserveSeats(int, java.lang.String)
	 */
	public String reserveSeats(String seatHoldId, String customerEmail) {
		if (VenueUtil.isEmpty(customerEmail)) {
			logger.error(BookingAppConstants.INVALID_INPUT);
			return BookingAppConstants.ERROR;
		}
		if (!VenueUtil.checkHold(seatHoldId, this.venue)) {
			logger.error(BookingAppConstants.INVALID_HOLD_ID);
			return BookingAppConstants.ERROR;
		}
		if (!customerEmail.equalsIgnoreCase(this.venue.getSeatHolds().get(seatHoldId).getCustomerEmail())) {
			logger.error(BookingAppConstants.EMAIL_ID_NOT_VALID_FOR_HOLD);
			return BookingAppConstants.ERROR;
		}

		synchronized (this.venue) {
			// Double check if the hold exists, as the Evictor could have claimed the seats held by now.
			if (!VenueUtil.checkHold(seatHoldId, this.venue)) {
				logger.error(BookingAppConstants.INVALID_HOLD_ID);
				return BookingAppConstants.ERROR;
			}
			SeatReserve seatReserve = VenueUtil.holdToReserved(this.venue.getSeatHolds().get(seatHoldId));
			this.venue.getSeatReserved().put(seatHoldId, seatReserve);
			this.venue.getSeatHolds().remove(seatHoldId);
		}
		logger.info(BookingAppConstants.BOOKING_ID_LOG + seatHoldId);
		logger.debug(BookingAppConstants.VENUE_SEAT_STATUS_LOG + venue);
		return seatHoldId;
	}

}
