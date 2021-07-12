package com.example.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.example.entities.Flight;

public interface FlightRepository {

	List<Flight> getAllFlights();
	Flight addFlight(Flight flight);
	Flight getFlightById(String flightId);
	Flight updateFlight(String flightId, Flight flight);
	void deleteFlight(String flightId);
}

/**
 * customer (Customer)
 * 		-customerId
 * 		-name
 * 		-address
 * 		-email
 * flight schedule (Flight)
 * 		-flightNumber
 * 		-sched
 * 		-price
 * transaction
 * 		book flight	(Booking)
 * 			-bookingNumber
 * 			-customer
 * 			-flight sched
 * 		payment (Payment)
 * 			-bookingNumber
 * 			-payment details
 * 			-status
 *		acknowlegdement/confirmation
 *			-bookingNumber
 *			-status
 *			-flight sched
 *			-customer
 */