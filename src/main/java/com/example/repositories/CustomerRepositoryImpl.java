package com.example.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.entities.Customer;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
	
	@PersistenceContext
	EntityManager em;

	public Customer getCustomer(int customerId) {
		
		return em.find(Customer.class, customerId);
	}
	
	public Customer getCustomerByEmail(String email) {
		Query getByEmailQuery = em.createQuery("from Customer where email = :email");
		getByEmailQuery.setParameter("email", email);
		
		return (Customer) getByEmailQuery.getSingleResult();
	}

	public Customer addCustomer(Customer customer) {
		em.persist(customer);
		return customer;
	}

	public Customer updateCustomer(Customer customer) {
		
		return em.merge(customer);
	}
	
	public void deleteCustomer(Customer customer) {
		
		em.remove(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		
		return (List<Customer>) em.createQuery("from Customer").getResultList();
	}

}
