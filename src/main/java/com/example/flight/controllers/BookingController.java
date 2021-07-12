package com.example.flight.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Booking;
import com.example.entities.BookingTransaction;
import com.example.flight.services.BookingService;

@RestController
@RequestMapping("/bookflight")
//@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class BookingController {
	
	private final static Logger log = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	BookingService bookingService;
	
	@Autowired
	BookingTransaction bookingTransaction;
	
	@Bean
	public BookingTransaction getBookingTransaction() {
		return new BookingTransaction();
	}
	
	@GetMapping("")
	public List<BookingTransaction> getAllBookingTransactions() {
		log.info("executing getAllBookingTransactions");
		return bookingService.getAllBookingTransactions();
	}
	
	@GetMapping("/{bookingId}")
	public BookingTransaction getBooking(@PathVariable("bookingId") String bookingId) {
		return bookingService.getBookingTransaction(bookingId);
	}
	
	@PostMapping("")
	public BookingTransaction addBooking(@RequestBody Booking booking) {
		log.info("executing addBooking");
		return bookingService.addBooking(booking);
	}
	
	@PutMapping("{bookingId}")
	public BookingTransaction updateBookingTransaction(@PathVariable("bookingId") String bookingId, @RequestBody BookingTransaction bookingTransaction) {
		log.info("executing updateBookingTransaction");
		return bookingService.updateBookingTransaction(bookingId, bookingTransaction);
	}
	
	@DeleteMapping("/{bookingId}")
	public String cancelBooking(@PathVariable("bookingId") String bookingId) {
		bookingService.cancelBooking(bookingId);
		return bookingId + " is cancelled"
				+ "\n"
				+ " ticket price will be refunded back to your payment account.  ";
	}
}
