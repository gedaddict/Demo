package com.example.flight.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Flight;
import com.example.flight.services.FlightService;

@RestController
@RequestMapping("/flights")
//@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class FlightController {

	@Autowired
	FlightService flightService;
	
	@GetMapping("")
	public List<Flight> getAllflights() {

		return flightService.getAllFlights();
	}
	
	@GetMapping("/{flightId}")
	public Flight getFlight(@PathVariable("flightId") String flightId) {
		
		return flightService.getFlight(flightId);
	}
	
	@PostMapping("")
	public Flight addFlight(@RequestBody Flight flight) {
		
		return flightService.addFlight(flight);
	}
	
	@PutMapping("/{flightId}")
	public Flight updateFlight(@PathVariable("flightId") String flightId, @RequestBody Flight flight) {
		
		return flightService.updateFlight(flightId, flight);
	}
	
	@DeleteMapping("/{flightId}")
	public String deleteFlight(@PathVariable("flightId") String flightId) {
		flightService.deleteFlight(flightId);
		return "Flight " + flightId + " deleted";
	}
}
