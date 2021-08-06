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
import com.example.flight.exceptions.AccountNumberDoesNotExist;
import com.example.flight.exceptions.CreditLimitReached;
import com.example.flight.exceptions.InsufficientCreditAvailable;
import com.example.flight.exceptions.IssuingBankDoesNotMatch;
import com.example.repositories.PaymentRepository;

@Service
public class PaymentService {
	
	private final static Logger log = LoggerFactory.getLogger(PaymentService.class);
	private final PaymentRepository paymentRepository;
	
	public PaymentService(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}
	
	@Autowired
	private Payment payment;
	
	public List<Payment> getAllPaymentAccounts() {
		log.info("PaymentService - getAllPaymentAccounts...");
		return paymentRepository.getAllPaymentAccounts();
	}
	
	public Payment getPaymentDetails(int cardNumber) {
		log.info("PaymentService - getPaymentDetails: " +cardNumber);
		return paymentRepository.getPaymentDetails(cardNumber);
	}
	
	public PaymentTransaction getPaymentTransaction (String transactionId) {
		log.info("PaymentService - getPaymentTransaction: " +transactionId);
		return paymentRepository.getPaymentTransaction(transactionId);
	}

	public PaymentTransaction processPayment(Booking booking, Customer customer, Double ticketPrice) {
		log.info("processing payment...");
		PaymentTransaction paymentTransaction = new PaymentTransaction();
		int cardNumber = booking.getPayment().getCardNumber();
		String bank = booking.getPayment().getBank();
		
		if (validatePayment(cardNumber, bank, ticketPrice)) {
			log.info("processing payment transaction...");
			paymentTransaction.setCustomer(customer);
			paymentTransaction.setPayment(payment);
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
			updatePaymentTransaction.setPayment(payment);
			paymentRepository.updatePaymentTransaction(updatePaymentTransaction);
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
		payment = this.getPaymentDetails(cardNumber);
		
		double creditBalance = payment.getCreditBalance();
		double creditAvailable = payment.getCreditAvailable();
		double creditLimit = payment.getCreditLimit();
		String issuingBank = payment.getBank();
		
		if (!(issuingBank.toLowerCase()).equals(bank.toLowerCase())) throw new IssuingBankDoesNotMatch("Issuing bank does not match.");
		
		if ((creditAvailable -= ticketPrice) < 0) throw new InsufficientCreditAvailable("Insufficient credit available.");
		
		creditBalance += ticketPrice;
		if (creditBalance >= creditLimit) throw new CreditLimitReached("You reached the credit limit.");
		
		payment.setCreditBalance(creditBalance);
		payment.setCreditAvailable(creditAvailable);
		return true;
	}
	
	@Transactional
	public Payment updatePaymentDetails(int accountNumber, Payment payment) {
		log.info("updating payment info...");
		
		//Payment paymentDetails = this.getPaymentDetails(accountNumber);
		payment = this.getPaymentDetails(accountNumber);
		
		if (payment == null) throw new AccountNumberDoesNotExist("account number does not exist.");
		
		payment.setBank(payment.getBank());
		payment.setCreditLimit(payment.getCreditLimit());
		payment.setCreditBalance(payment.getCreditBalance());
		payment.setCreditAvailable(payment.getCreditAvailable());
		
		return paymentRepository.updatePaymentDetails(payment);
	}
	
	@Transactional
	public void cancelPaymentAccount(int accountNumber) {
		payment = this.getPaymentDetails(accountNumber);
		paymentRepository.cancelPaymentAccount(payment);
	}
}
