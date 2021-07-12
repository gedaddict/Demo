package com.example.flight.services;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Flight;
import com.example.repositories.FlightRepository;

@Service
public class FlightService {

	@Autowired
	FlightRepository flightRepository;
	
	private Flight flight;
	
	public List<Flight> getAllFlights(){
		
		return flightRepository.getAllFlights();
	}
	
	public Flight getFlight(String flightId) {
		flight = flightRepository.getFlightById(flightId);
		if (flight == null) throw new RuntimeException("Flight does not exist. ");
		return flight;
	}
	
	@Transactional
	public Flight addFlight(Flight flight) {
		
		return flightRepository.addFlight(flight);
	}
	
	@Transactional
	public Flight updateFlight(String flightId, Flight flight) {
		
		return flightRepository.updateFlight(flightId, flight);
	}
	@Transactional
	public void deleteFlight(String flightId) {
		flightRepository.deleteFlight(flightId);
	}
}
