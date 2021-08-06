package com.example.flight.exceptions;

public class IssuingBankDoesNotMatch extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7960762649442529022L;
	
	public IssuingBankDoesNotMatch(String message) {
		super(message);
	}

}
