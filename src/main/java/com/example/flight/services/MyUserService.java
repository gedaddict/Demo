package com.example.flight.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entities.MyUser;
import com.example.entities.MyUserDetails;
import com.example.entities.MyUserRole;
import com.example.repositories.MyUserRepository;

@Service
@Transactional
public class MyUserService implements UserDetailsService {

	private MyUser myUser;
	
	@Autowired
	MyUserRepository myUserRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		myUser = myUserRepository.getUser(username);
		return new MyUserDetails(myUser, getUserRoles(myUser));
	}
	
	public boolean validateUser(String username, String password) {
		MyUser userCredentials = this.getUser(username);
		if (userCredentials.getPassword().equals(password))
			return true;
		else
			return false;
	}
	
	private List<String> getUserRoles(MyUser user) {
		List<String> roles = new ArrayList<>();
		for (MyUserRole role : user.getRoles()) {
			roles.add(role.getRole());
		}
		return roles;
	}
	
	public List<MyUser> getAllUsers() {
		return myUserRepository.getAllUsers();
	}
	
	public MyUser getUser(String username) {
		return myUserRepository.getUser(username);
	}
	
	public MyUser addUser(MyUser user) {
		String password = user.getPassword();
		user.setPassword(passwordEncoder.encode(password));
		return myUserRepository.addUser(user);
	}
	
	public MyUser updateUser(String username, MyUser user) {
		myUser = myUserRepository.getUser(username);
		String password = user.getPassword();
		user.setPassword(passwordEncoder.encode(password));
		myUser = user;
		return myUserRepository.updateUser(myUser);
	}
	
	public void deleteUser(String username) {
		myUser = myUserRepository.getUser(username);
		myUserRepository.deleteUser(myUser);
	}
}
