package com.example.flight.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.Booking;
import com.example.entities.BookingTransaction;
import com.example.entities.Customer;
import com.example.entities.Flight;
import com.example.entities.Payment;
import com.example.entities.PaymentTransaction;
import com.example.flight.controllers.BookingController;
import com.example.repositories.BookingRepository;

@Service
public class BookingService {

	private final static Logger log = LoggerFactory.getLogger(BookingService.class);
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	FlightService flightService;
	
	private Customer bookingCustomer;
	private Flight flight;
	private BookingTransaction bookingTransaction;;
	private PaymentTransaction paymentTransaction;
	
	public List<BookingTransaction> getAllBookingTransactions() {
		return bookingRepository.getAllBookingTransactions();
	}
	
	public BookingTransaction getBookingTransaction(String bookingId) {
		return bookingRepository.getBookingTransaction(bookingId);
	}
	
	@Transactional
	public BookingTransaction addBooking(Booking booking) {
		log.info("executing addBooking...");
		bookingCustomer = customerService.addCustomer(booking.getCustomer());
		flight = flightService.getFlight(booking.getFlight().getFlightId());
		paymentTransaction = paymentService.processPayment(booking, bookingCustomer);
		bookingTransaction.setCustomer(bookingCustomer);
		bookingTransaction.setFlight(flight);
		bookingTransaction.setTransactionId(paymentTransaction);
		
		return bookingRepository.addBookingTransaction(bookingTransaction);
	}
	
	@Transactional
	public BookingTransaction updateBookingTransaction(String bookingId, BookingTransaction bookingTransaction) {
		log.info("executing updateBookingTransaction");
		BookingTransaction updateBookingTransaction = this.getBookingTransaction(bookingId);
		//bookingCustomer = customerService.getCustomerByEmail(bookingTransaction.getCustomer().getEmail());
		flight = flightService.getFlight(bookingTransaction.getFlight().getFlightId());
		updateBookingTransaction.setFlight(flight);
		PaymentTransaction updatePaymentTransaction = paymentService.updatePaymentTransaction(bookingTransaction, flight, updateBookingTransaction.getCustomer());
		updateBookingTransaction.setTransactionId(updatePaymentTransaction);
		
		return bookingRepository.addBookingTransaction(updateBookingTransaction);
	}
	
	@Transactional
	public void cancelBooking(String bookingId) {
		log.info("cancelling Booking...");
		bookingTransaction = this.getBookingTransaction(bookingId);
		
		bookingRepository.cancelBooking(bookingTransaction);
		log.info("processing payment transaction refund...");
		paymentService.processRefund(bookingTransaction);
	}
	
}
