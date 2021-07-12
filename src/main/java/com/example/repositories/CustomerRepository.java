package com.example.repositories;

import java.util.List;

import com.example.entities.Customer;

public interface CustomerRepository {

	public List<Customer> getAllCustomers();
	public Customer getCustomer(int customerId);
	public Customer getCustomerByEmail(String email);
	public Customer addCustomer(Customer customer);
	public Customer updateCustomer(Customer customer);
	public void deleteCustomer(Customer customer);
}
