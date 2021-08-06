package com.example.flight.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.entities.BookingTransaction;
import com.example.entities.Customer;
import com.example.entities.Flight;
import com.example.entities.MyUser;
import com.example.entities.Payment;
import com.example.entities.PaymentTransaction;

@Configuration
public class AppConfig {
	
	@Bean
	public BookingTransaction getBookingTransaction() {
		return new BookingTransaction();
	}
	@Bean
	public PaymentTransaction getPaymentTransaction() {
		return new PaymentTransaction();
	}
	
	@Bean
	public Customer getCustomer() {
		return new Customer();
	}
	
	@Bean
	public Flight getFlight() {
		return new Flight();
	}
	
	@Bean
	public Payment getPayment() {
		return new Payment();
	}
	
	@Bean
	public MyUser getMyUser() {
		return new MyUser();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
