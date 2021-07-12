package com.example.repositories;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.entities.Flight;

@Repository
public class FlightRepositoryImpl implements FlightRepository{
	
	@PersistenceContext
	EntityManager em;
	
	private Flight f;

	public List<Flight> getAllFlights() {
		Query q = em.createQuery("from Flight");
		List<Flight> allFlights = q.getResultList();
		return allFlights; 
	}

	public Flight addFlight(Flight flight) {
		em.persist(flight);
		return flight;
	}

	public Flight getFlightById(String flightId) {
		return em.find(Flight.class, flightId);
	}

	public Flight updateFlight(String flightId, Flight flight) {
		f = em.find(Flight.class, flightId);
		f = flight;
		em.merge(f);
		return flight;
	}

	public void deleteFlight(String flightId) {
		f = em.find(Flight.class, flightId);
		em.remove(f);
		
	}

}
