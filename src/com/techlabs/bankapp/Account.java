package com.techlabs.bankapp;

public class Account {
	private int accountId;
	private int accountNumber;
    private double amount;
	public Account(int accountId, int accountNumber, double amount) {
		super();
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.amount = amount;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}


}
