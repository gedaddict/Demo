package com.example.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.entities.Payment;
import com.example.entities.PaymentTransaction;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository{

	@PersistenceContext
	EntityManager em;

	public Payment getPaymentDetails(int cardNumber) {
		
		return em.find(Payment.class, cardNumber);
	}

	public Payment updatePaymentDetails(Payment payment) {
		
		return em.merge(payment);
	}

	public PaymentTransaction addPaymentTransaction(PaymentTransaction paymentTransaction) {
		if (paymentTransaction.getTransactionId().isEmpty())
			em.persist(paymentTransaction);
		else
			em.merge(paymentTransaction);
		return paymentTransaction;
	}
	
	public PaymentTransaction getPaymentTransaction(String transactionId) {
		return em.find(PaymentTransaction.class, transactionId);
	}

	public List<Payment> getAllPaymentAccounts() {
		List<Payment> accounts = em.createQuery("from Payment").getResultList();
		return accounts;
	}

	public void cancelPaymentAccount(Payment payment) {
		em.remove(payment);
	}
	
	public void cancelPaymentTransaction(PaymentTransaction paymentTransaction) {
		em.remove(paymentTransaction);
	}
}
