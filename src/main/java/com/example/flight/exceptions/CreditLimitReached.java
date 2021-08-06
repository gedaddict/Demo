package com.example.flight.exceptions;

public class CreditLimitReached extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1195803963362778735L;

	public CreditLimitReached(String message) {
		super(message);
	}
}
