package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Payment {

	@Id
	@GeneratedValue
	private int cardNumber;
	private String bank;
	//@JsonIgnore
	private double creditLimit;
	//@JsonIgnore
	private double creditBalance;
	private double creditAvailable;
	
	public int getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	public double getCreditBalance() {
		return creditBalance;
	}
	public void setCreditBalance(double creditBalance) {
		this.creditBalance = creditBalance;
	}
	public double getCreditAvailable() {
		return creditAvailable;
	}
	public void setCreditAvailable(double creditAvailable) {
		this.creditAvailable = creditAvailable;
	}
	@Override
	public String toString() {
		return "Payment [cardNumber=" + cardNumber + ", bank=" + bank + ", creditLimit=" + creditLimit
				+ ", creditBalance=" + creditBalance + ", creditAvailable=" + creditAvailable + "]";
	}
	
}
