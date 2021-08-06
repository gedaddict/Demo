package com.example.flight.exceptions;

public class AccountNumberDoesNotExist extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7911121342637675184L;
	
	public AccountNumberDoesNotExist(String message) {
		super(message);
	}

}
