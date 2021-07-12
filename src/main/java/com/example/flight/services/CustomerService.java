package com.example.flight.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entities.Customer;
import com.example.repositories.CustomerRepository;

@Service
public class CustomerService {
	
	private final static Logger log = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private Customer customerByEmail;
	
	public Customer addCustomer(Customer customer) {
		log.info("executing addCustomer");
		if (customer.getEmail().isEmpty())	throw new RuntimeException("Email is required. ");
		String email = customer.getEmail();
		try {
			customerByEmail = customerRepository.getCustomerByEmail(email);
			customer = customerByEmail;
		} catch (Exception e) {
			log.info("creating new customer...");
			customer = customerRepository.addCustomer(customer);
		}
		
		return customer;
	}
	
	
	public Customer getCustomerByEmail(String email) {
		if (email.isEmpty()) throw new RuntimeException("Email is required. ");
		return customerRepository.getCustomerByEmail(email);
	}
	
	public List<Customer> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}
	@Transactional
	public Customer updateCustomer(String username, Customer customer) {
		customerByEmail = this.getCustomerByEmail(username);
		customerByEmail = customer;
		return customerRepository.updateCustomer(customerByEmail);
	}
	@Transactional
	public void deleteCustomer(int userId) {
		customerByEmail = customerRepository.getCustomer(userId);
		customerRepository.deleteCustomer(customerByEmail);
	}
}
