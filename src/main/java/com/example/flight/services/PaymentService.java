package com.example.flight.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.entities.Booking;
import com.example.entities.BookingTransaction;
import com.example.entities.Customer;
import com.example.entities.Flight;
import com.example.entities.Payment;
import com.example.entities.PaymentTransaction;
import com.example.repositories.PaymentRepository;

@Service
public class PaymentService {
	
	private final static Logger log = LoggerFactory.getLogger(PaymentService.class);
	
	private static Payment paymentDetails;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	PaymentTransaction paymentTransaction;
	
	@Bean
	public PaymentTransaction getPaymentTransaction() {
		return new PaymentTransaction();
	}
	
	public List<Payment> getAllPaymentAccounts() {
		
		return paymentRepository.getAllPaymentAccounts();
	}
	
	public Payment getPaymentDetails(int cardNumber) {
		
		return paymentRepository.getPaymentDetails(cardNumber);
	}
	
	public PaymentTransaction getPaymentTransaction (String transactionId) {
		
		return paymentRepository.getPaymentTransaction(transactionId);
	}

	public PaymentTransaction processPayment(Booking booking, Customer customer) {
		log.info("processing payment...");
		int cardNumber = booking.getPayment().getCardNumber();
		String bank = booking.getPayment().getBank();
		Double ticketPrice = booking.getFlight().getPrice();
		
		if (validatePayment(cardNumber, bank, ticketPrice)) {
			log.info("processing payment transaction...");
			paymentTransaction.setCustomer(customer);
			paymentTransaction.setPayment(paymentDetails);
			paymentRepository.addPaymentTransaction(paymentTransaction);
		}
			
		return paymentTransaction;
	}
	
	public PaymentTransaction updatePaymentTransaction(BookingTransaction bookingTransaction, Flight flight, Customer customer) {
		log.info("processing update PaymentTransaction...");
		PaymentTransaction updatePaymentTransaction = null;
		int cardNumber = bookingTransaction.getTransactionId().getPayment().getCardNumber();
		String bank = bookingTransaction.getTransactionId().getPayment().getBank();
		Double ticketPrice = flight.getPrice();
		
		if (validatePayment(cardNumber, bank, ticketPrice)) {
			log.info("updating payment transaction...");
			updatePaymentTransaction = this.getPaymentTransaction(bookingTransaction.getTransactionId().getTransactionId());
			//updatePaymentTransaction.setCustomer(customer);
			updatePaymentTransaction.setPayment(paymentDetails);
			paymentRepository.addPaymentTransaction(updatePaymentTransaction);
		}
			
		return updatePaymentTransaction;
	}
	
	@Transactional
	public Payment processRefund(BookingTransaction bookingTransaction) {
		log.info("cancelling payment transaction...");
		PaymentTransaction paymentTransaction = this.getPaymentTransaction(bookingTransaction.getTransactionId().getTransactionId());
		paymentRepository.cancelPaymentTransaction(paymentTransaction);
		log.info("processing refund...");
		int cardNumber = paymentTransaction.getPayment().getCardNumber();
		Payment refundToAccount = paymentRepository.getPaymentDetails(cardNumber);
		log.info("refunding to account..." + refundToAccount);
		Double refundedTicketPrice = bookingTransaction.getFlight().getPrice();
		log.info("refunding to ticket price..." + refundedTicketPrice);
		double creditAvailable = refundToAccount.getCreditAvailable();
		double creditBalance = refundToAccount.getCreditBalance();
		refundToAccount.setCreditAvailable(creditAvailable + refundedTicketPrice);
		refundToAccount.setCreditBalance(creditBalance - refundedTicketPrice);
		paymentRepository.updatePaymentDetails(refundToAccount);
		return refundToAccount;
	}
	
	private boolean validatePayment(int cardNumber, String bank, Double ticketPrice) {
		log.info("validating payment info...");
		paymentDetails = this.getPaymentDetails(cardNumber);
		
		double creditBalance = paymentDetails.getCreditBalance();
		double creditAvailable = paymentDetails.getCreditAvailable();
		double creditLimit = paymentDetails.getCreditLimit();
		String issuingBank = paymentDetails.getBank();
		
		if (!issuingBank.equals(bank)) throw new RuntimeException("Issuing bank does not match.");
		
		creditBalance += ticketPrice;
		if (creditBalance >= creditLimit) throw new RuntimeException("You reached the credit limit.");
	
		if ((creditAvailable -= ticketPrice) < 0) throw new RuntimeException("Insufficient credit balance.");
		
		paymentDetails.setCreditBalance(creditBalance);
		paymentDetails.setCreditAvailable(creditAvailable);
		return true;
	}
	
	@Transactional
	public Payment updatePaymentDetails(int accountNumber, Payment payment) {
		log.info("updating payment info...");
		
		Payment paymentDetails = this.getPaymentDetails(accountNumber);
		
		if (paymentDetails == null) throw new RuntimeException("account number does not exist.");
		
		paymentDetails.setBank(payment.getBank());
		paymentDetails.setCreditLimit(payment.getCreditLimit());
		paymentDetails.setCreditBalance(payment.getCreditBalance());
		paymentDetails.setCreditAvailable(payment.getCreditAvailable());
		
		return paymentRepository.updatePaymentDetails(paymentDetails);
	}
	
	@Transactional
	public void cancelPaymentAccount(int accountNumber) {
		paymentDetails = this.getPaymentDetails(accountNumber);
		paymentRepository.cancelPaymentAccount(paymentDetails);
	}
}
