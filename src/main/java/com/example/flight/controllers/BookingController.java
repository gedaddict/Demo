package com.example.flight.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class BookingController {
	
	private final static Logger log = LoggerFactory.getLogger(BookingController.class);
	private final BookingService bookingService;
	
	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	
	@GetMapping("")
	public ResponseEntity<List<BookingTransaction>> getAllBookingTransactions() {
		log.info("executing getAllBookingTransactions... ");
		return new ResponseEntity<List<BookingTransaction>>(bookingService.getAllBookingTransactions(), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{bookingId}")
	public ResponseEntity<BookingTransaction> getBooking(@PathVariable("bookingId") String bookingId) {
		log.info("executing getBooking... ");
		return new ResponseEntity<BookingTransaction>(bookingService.getBookingTransaction(bookingId), HttpStatus.ACCEPTED);
	}
	
	@PostMapping("")
	public ResponseEntity<BookingTransaction> addBooking(@RequestBody Booking booking) {
		log.info("executing addBooking... ");
		return new ResponseEntity<BookingTransaction>(bookingService.addBooking(booking), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("{bookingId}")
	public ResponseEntity<BookingTransaction> updateBookingTransaction(@PathVariable("bookingId") String bookingId, @RequestBody BookingTransaction bookingTransaction) {
		log.info("executing updateBookingTransaction... ");
		return new ResponseEntity<BookingTransaction>(bookingService.updateBookingTransaction(bookingId, bookingTransaction), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{bookingId}")
	public ResponseEntity<Map<String, String>> cancelBooking(@PathVariable("bookingId") String bookingId) {
		log.info("executing cancelBooking... ");
		bookingService.cancelBooking(bookingId);
		HashMap<String, String> map = new HashMap<>();
		map.put("bookingId", bookingId);
		map.put("message", "booking: "+bookingId +" is now cancelled"
				+ " ticket price will be refunded back to your payment account.  ");
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.ACCEPTED);
	}
}
