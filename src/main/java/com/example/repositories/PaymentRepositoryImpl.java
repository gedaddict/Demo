package com.example.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.entities.Payment;
import com.example.entities.PaymentTransaction;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository{
	
	private static final Logger log = LoggerFactory.getLogger(PaymentRepositoryImpl.class);

	@PersistenceContext
	private EntityManager em;

	public Payment getPaymentDetails(int cardNumber) {
		log.info("PaymentRepositoryImpl - getPaymentDetails: "+cardNumber);
		return em.find(Payment.class, cardNumber);
	}

	public Payment updatePaymentDetails(Payment payment) {
		log.info("PaymentRepositoryImpl - updatePaymentDetails: "+payment.toString());
		return em.merge(payment);
	}

	public PaymentTransaction addPaymentTransaction(PaymentTransaction paymentTransaction) {
		log.info("PaymentRepositoryImpl - addPaymentTransaction: "+paymentTransaction.toString());
		em.persist(paymentTransaction);
		return paymentTransaction;
	}
	
	public PaymentTransaction updatePaymentTransaction(PaymentTransaction paymentTransaction) {
		log.info("PaymentRepositoryImpl - updatePaymentTransaction: "+paymentTransaction.toString());
		return em.merge(paymentTransaction);
	}
	
	public PaymentTransaction getPaymentTransaction(String transactionId) {
		log.info("PaymentRepositoryImpl - getPaymentTransaction: "+transactionId);
		return em.find(PaymentTransaction.class, transactionId);
	}

	public List<Payment> getAllPaymentAccounts() {
		List<Payment> accounts = em.createQuery("from Payment").getResultList();
		return accounts;
	}

	public void cancelPaymentAccount(Payment payment) {
		log.info("PaymentRepositoryImpl - getPaymentTransaction: "+payment.toString());
		em.remove(payment);
	}
	
	public void cancelPaymentTransaction(PaymentTransaction paymentTransaction) {
		log.info("PaymentRepositoryImpl - getPaymentTransaction: "+paymentTransaction.toString());
		em.remove(paymentTransaction);
	}
}
