package com.example.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Entity
public class MyUser {

	@Id
	@GeneratedValue(generator = "increment")
	private int userId;
	private String username;
	private String password;
	private boolean active;
	
	@ElementCollection
	@JoinTable(name="MyUserRole", 
				joinColumns=@JoinColumn(name="username"))
	private List<MyUserRole> roles = new ArrayList<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<MyUserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<MyUserRole> roles) {
		this.roles = roles;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "MyUser [userId=" + userId + ", username=" + username + ", password=" + password + ", active=" + active
				+ ", roles=" + roles + "]";
	}
}
