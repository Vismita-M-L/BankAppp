package com.techlabs.bankapp;

import java.util.Date;

public class Transaction {
	
	    private int transactionId;
	    private String transactionType;
	    private Date date;
	    private double amount;
	    private int senderAccountNumber;
	    private int receiverAccountNumber;

	    public Transaction() {
	        this.transactionId = transactionId;
	        this.transactionType = transactionType;
	        this.date = date;
	        this.amount = amount;
	        this.senderAccountNumber = senderAccountNumber;
	        this.receiverAccountNumber = receiverAccountNumber;
	    }

		public int getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(int transactionId) {
			this.transactionId = transactionId;
		}

		public String getTransactionType() {
			return transactionType;
		}

		public void setTransactionType(String transactionType) {
			this.transactionType = transactionType;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public int getSenderAccountNumber() {
			return senderAccountNumber;
		}

		public void setSenderAccountNumber(int senderAccountNumber) {
			this.senderAccountNumber = senderAccountNumber;
		}

		public int getReceiverAccountNumber() {
			return receiverAccountNumber;
		}

		public void setReceiverAccountNumber(int receiverAccountNumber) {
			this.receiverAccountNumber = receiverAccountNumber;
		}
	    

	    
}
    
    
  

  