package com.example.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.entities.Booking;
import com.example.entities.BookingTransaction;

public interface BookingRepository {

	public List<BookingTransaction> getAllBookingTransactions();
	public BookingTransaction getBookingTransaction(String bookingId);
	public BookingTransaction addBookingTransaction(BookingTransaction bookingTransaction);
	public Booking updateBooking(String bookingId, Booking booking);
	public void cancelBooking(BookingTransaction bookingTransaction);
}
