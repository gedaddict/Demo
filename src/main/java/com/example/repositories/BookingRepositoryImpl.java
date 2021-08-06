package com.example.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.entities.Booking;
import com.example.entities.BookingTransaction;

@Repository
public class BookingRepositoryImpl implements BookingRepository{
	
	private static final Logger log = LoggerFactory.getLogger(BookingRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	public List<BookingTransaction> getAllBookingTransactions() {
		log.info("executing BookingRepositoryImpl - getAllBookingTransactions...");
		List<BookingTransaction> resultList = em.createQuery("from BookingTransaction").getResultList();
		return resultList;
	}

	public BookingTransaction getBookingTransaction(String bookingId) {
		log.info("executing BookingRepositoryImpl - getBookingTransaction: " +bookingId);
		return em.find(BookingTransaction.class, bookingId);
	}

	public BookingTransaction addBookingTransaction(BookingTransaction bookingTransaction) {
		log.info("executing BookingRepositoryImpl - addBookingTransaction: " +bookingTransaction.toString());
		em.persist(bookingTransaction);
		return bookingTransaction;
	}
	
	public BookingTransaction updateBookingTransaction(BookingTransaction bookingTransaction) {
		log.info("executing BookingRepositoryImpl - updateBookingTransaction: " +bookingTransaction.toString());
		return em.merge(bookingTransaction);
	}

	public Booking updateBooking(String bookingId, Booking booking) {
		// TODO Auto-generated method stub
		return null;
	}

	public void cancelBooking(BookingTransaction bookingTransaction) {
		log.info("executing BookingRepositoryImpl - cancelBooking: " +bookingTransaction.toString());
		em.remove(bookingTransaction);
	}

}
