package com.example.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class BookingTransaction {

	@Id
	private String bookingId = UUID.randomUUID().toString().substring(0, 7);
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "flightId")
	private Flight flight;
	
	
	@ManyToOne
	@JoinColumn(name = "transactionId")
	private PaymentTransaction transactionId;
	
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public PaymentTransaction getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(PaymentTransaction transactionId) {
		this.transactionId = transactionId;
	}
}
