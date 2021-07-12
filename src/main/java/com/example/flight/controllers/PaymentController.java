package com.example.flight.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Payment;
import com.example.flight.services.PaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {

	@Autowired
	PaymentService paymentService;
	
	
	@GetMapping("")
	public List<Payment> getAllPaymentAccounts() {
		return paymentService.getAllPaymentAccounts();
	}
	
	@GetMapping("/{accountNumber}")
	public Payment getPaymentDetails(@PathVariable ("accountNumber") int accountNumber) {
		return paymentService.getPaymentDetails(accountNumber);
	}
	
	@PutMapping("/{accountNumber}")
	public Payment updatePaymentDetails(@RequestBody Payment payment, @PathVariable("accountNumber") int accountNumber) {
		return paymentService.updatePaymentDetails(accountNumber, payment);
	}
	
	@DeleteMapping("{accountNumber}")
	public String cancelPaymentAccount(@PathVariable("accountNumber") int accountNumber) {
		paymentService.cancelPaymentAccount(accountNumber);
		return "account " + accountNumber + " cancelled"; 
	}
}
