package com.example.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

	private String username;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities = new ArrayList<>();
	
	public MyUserDetails() {
		
	}
	
	public MyUserDetails(MyUser user, List<String> roles) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.active = user.isActive();
		this.authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	
	public String getPassword() {
		
		return password;
	}

	
	public String getUsername() {
	
		return username;
	}

	
	public boolean isAccountNonExpired() {
		
		return active;
	}

	
	public boolean isAccountNonLocked() {
		
		return active;
	}

	
	public boolean isCredentialsNonExpired() {
		
		return active;
	}

	
	public boolean isEnabled() {
		
		return active;
	}

}
