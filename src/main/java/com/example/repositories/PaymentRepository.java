package com.example.repositories;

import java.util.List;

import com.example.entities.Payment;
import com.example.entities.PaymentTransaction;

public interface PaymentRepository {

	public List<Payment> getAllPaymentAccounts();
	public Payment getPaymentDetails(int cardNumber);
	public Payment updatePaymentDetails(Payment payment);
	public void cancelPaymentAccount(Payment payment);
	public PaymentTransaction addPaymentTransaction(PaymentTransaction PaymentTransaction);
	public PaymentTransaction getPaymentTransaction(String transactionId);
	public void cancelPaymentTransaction(PaymentTransaction paymentTransaction);
}
