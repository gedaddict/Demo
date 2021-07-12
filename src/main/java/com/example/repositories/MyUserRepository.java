package com.example.repositories;

import java.util.List;

import com.example.entities.MyUser;

public interface MyUserRepository {

	public List<MyUser> getAllUsers();
	public MyUser getUser(String username);
	public MyUser addUser(MyUser user);
	public MyUser updateUser(MyUser user);
	public void deleteUser(MyUser user);
}
