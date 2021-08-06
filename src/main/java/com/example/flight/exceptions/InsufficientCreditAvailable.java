package com.example.flight.exceptions;

public class InsufficientCreditAvailable extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2501847043393303106L;
	
	public InsufficientCreditAvailable(String message) {
		super(message);
	}

}
