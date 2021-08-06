package com.example.flight.advices;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.flight.exceptions.AccountNumberDoesNotExist;
import com.example.flight.exceptions.CreditLimitReached;
import com.example.flight.exceptions.InsufficientCreditAvailable;
import com.example.flight.exceptions.IssuingBankDoesNotMatch;

@ControllerAdvice
public class FlightControllerAdvice {

	private static final Logger log = LoggerFactory.getLogger(FlightControllerAdvice.class);
	private HashMap<String, String> map = new HashMap<String, String>();
	
	@ExceptionHandler(IssuingBankDoesNotMatch.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ResponseEntity<Map<String, String>> handleIssuingBankDoesNotMatch(HttpServletRequest req, IssuingBankDoesNotMatch e) {
		log.info("handleIssuingBankDoesNotMatch...");
		map.put("status", String.valueOf(HttpStatus.CONFLICT));
		map.put("error", e.getLocalizedMessage());
		map.put("URI", req.getRequestURI());

		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(CreditLimitReached.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleCreditLimitReached(HttpServletRequest req, CreditLimitReached e) {
		log.info("handleCreditLimitReached...");
		map.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
		map.put("error", e.getLocalizedMessage());
		map.put("URI", req.getRequestURI());

		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InsufficientCreditAvailable.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public ResponseEntity<Map<String, String>> handleInsufficientCreditAvailable(HttpServletRequest req, InsufficientCreditAvailable e) {
		log.info("handleInsufficientCreditAvailable...");
		map.put("status", String.valueOf(HttpStatus.FORBIDDEN));
		map.put("error", e.getLocalizedMessage());
		map.put("URI", req.getRequestURI());

		return new ResponseEntity<Map<String,String>>(map, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(AccountNumberDoesNotExist.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Map<String, String>> handleAccountNumberDoesNotExist(HttpServletRequest req, AccountNumberDoesNotExist e) {
		log.info("handleAccountNumberDoesNotExist...");
		map.put("status", String.valueOf(HttpStatus.FORBIDDEN));
		map.put("error", e.getLocalizedMessage());
		map.put("URI", req.getRequestURI());
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.BAD_REQUEST);
	}
}
