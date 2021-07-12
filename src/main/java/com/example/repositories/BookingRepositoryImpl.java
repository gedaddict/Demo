package com.example.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.entities.Booking;
import com.example.entities.BookingTransaction;

@Repository
public class BookingRepositoryImpl implements BookingRepository{
	
	@PersistenceContext
	EntityManager em;

	public List<BookingTransaction> getAllBookingTransactions() {
		List<BookingTransaction> resultList = em.createQuery("from BookingTransaction").getResultList();
		return resultList;
	}

	public BookingTransaction getBookingTransaction(String bookingId) {
		return em.find(BookingTransaction.class, bookingId);
	}

	public BookingTransaction addBookingTransaction(BookingTransaction bookingTransaction) {
		if (bookingTransaction.getBookingId().isEmpty())
			em.persist(bookingTransaction);
		else
			em.merge(bookingTransaction);
		return bookingTransaction;
	}

	public Booking updateBooking(String bookingId, Booking booking) {
		// TODO Auto-generated method stub
		return null;
	}

	public void cancelBooking(BookingTransaction bookingTransaction) {
		em.remove(bookingTransaction);
	}

}
