package com.example.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.example.entities.MyUser;
@Repository
public class MyUserRepositoryImpl implements MyUserRepository{
	
	@PersistenceContext
	EntityManager em;

	@Override
	public List<MyUser> getAllUsers() {
		
		return (List<MyUser>) em.createQuery("from MyUser").getResultList();
	}

	@Override
	public MyUser getUser(String username) {
		Query createQuery = em.createQuery("from MyUser where username = :username");
		createQuery.setParameter("username", username);
		
		return (MyUser) createQuery.getSingleResult();
	}

	@Override
	public MyUser addUser(MyUser user) {
		em.persist(user);
		return user;
	}

	@Override
	public MyUser updateUser(MyUser user) {
		
		return em.merge(user);
	}

	@Override
	public void deleteUser(MyUser user) {
		em.remove(user);
		
	}

}
