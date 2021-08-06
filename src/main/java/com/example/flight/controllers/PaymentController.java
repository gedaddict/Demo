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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Payment;
import com.example.flight.services.PaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {

	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
	private final PaymentService paymentService;
	
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@GetMapping("")
	public ResponseEntity<List<Payment>> getAllPaymentAccounts() {
		log.info("PaymentController - getAllPaymentAccounts... ");
		return new ResponseEntity<List<Payment>>(paymentService.getAllPaymentAccounts(), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Payment> getPaymentDetails(@PathVariable ("accountNumber") int accountNumber) {
		log.info("PaymentController - getPaymentDetails: " +accountNumber);
		return new ResponseEntity<Payment>(paymentService.getPaymentDetails(accountNumber), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{accountNumber}")
	public ResponseEntity<Payment> updatePaymentDetails(@RequestBody Payment payment, @PathVariable("accountNumber") int accountNumber) {
		log.info("PaymentController - updatePaymentDetails: " +accountNumber);
		return new ResponseEntity<Payment>(paymentService.updatePaymentDetails(accountNumber, payment), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("{accountNumber}")
	public ResponseEntity<Map<String, String>> cancelPaymentAccount(@PathVariable("accountNumber") int accountNumber) {
		log.info("PaymentController - cancelPaymentAccount: " +accountNumber);
		HashMap<String, String> map = new HashMap<>();
		map.put("accountNumber", String.valueOf(accountNumber));
		map.put("message", "account " + accountNumber + " cancelled");
		paymentService.cancelPaymentAccount(accountNumber);
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.ACCEPTED); 
	}
}
