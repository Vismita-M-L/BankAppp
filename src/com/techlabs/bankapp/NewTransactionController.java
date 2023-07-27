package com.techlabs.bankapp;

import java.io.IOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/NewTransactionController")
public class NewTransactionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public NewTransactionController() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String customerIdStr = request.getParameter("CustomerId");
	        String transactionType = request.getParameter("TransactionType");
	        String amountStr = request.getParameter("Amount");
	        String receiverAccountNumberStr = request.getParameter("ReceiverAccountNumber");

	        if (customerIdStr == null || transactionType == null || amountStr == null) {
	            // Invalid form submission
	            response.sendRedirect("CustomerHome.jsp?error=1");
	            return;
	        }

	        int customerId = Integer.parseInt(customerIdStr);
	        double amount = Double.parseDouble(amountStr);

	        if (transactionType.equals("transfer") && receiverAccountNumberStr == null) {
	            // For transfer transaction, receiver account number must be specified
	            response.sendRedirect("CustomerHome.jsp?error=2");
	            return;
	        }
	        Connection connection = null;
	        try {
	            if (transactionType.equals("transfer")) {
	                int receiverAccountNumber = Integer.parseInt(receiverAccountNumberStr);
	                if (performTransfer(customerId, receiverAccountNumber, amount)) {
	                    // Transfer successful
	                    request.setAttribute("message", "Transfer successful");
	                } else {
	                    // Transfer failed (insufficient balance)
	                    response.sendRedirect("CustomerHome.jsp?error=3");
	                    return;
	                }
	            } else {
	                if (performCreditDebit(customerId, transactionType, amount)) {
	                    // Credit/Debit transaction successful
	                    request.setAttribute("message", "Transaction successful");
	                } else {
	                    // Credit/Debit transaction failed (insufficient balance)
	                    response.sendRedirect("CustomerHome.jsp?error=3");
	                    return;
	                }
	            }

	            // Retrieve the updated account number and balance for the customer
	            double updatedBalance = getAccountBalance(connection, customerId);
	            request.setAttribute("customer", customerId);
	            request.setAttribute("balance", updatedBalance);

	            // Redirect back to the CustomerHome.jsp page
	            request.getRequestDispatcher("CustomerHome.jsp").forward(request, response);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.sendRedirect("CustomerHome.jsp?error=4");
	        }
	}
	    private boolean performTransfer(int senderCustomerId, int receiverAccountNumber, double amount) throws SQLException {
	    	 Connection connection = null;
	    	 try {
	    		 connection = DBConnection.connect();
	            // Perform transfer transaction updates here

	            // 1. Check if the sender has sufficient balance for transfer
	            double senderBalance = getAccountBalance( connection, senderCustomerId);
	            if (senderBalance < amount) {
	                return false; // Transfer failed, insufficient balance
	            }

	            // 2. Update sender's balance (debit)
	            updateBalance( connection, senderCustomerId, -amount);

	            // 3. Update receiver's balance (credit)
	            updateBalance( connection, receiverAccountNumber, amount);

	            // 4. Save the transfer transaction details in the database
	            String insertTransactionQuery = "INSERT INTO transaction (TransactionType, Date, Amount, SenderAccountNumber, ReceiverAccountNumber) VALUES (?, ?, ?, ?, ?)";
	            try (PreparedStatement insertStatement =  connection.prepareStatement(insertTransactionQuery)) {
	                insertStatement.setString(1, "transfer"); // Assuming "transfer" is the transaction type for transfers
	                insertStatement.setDate(2,  Date.valueOf(LocalDate.now())); 
	                insertStatement.setDouble(3, amount);
	                insertStatement.setInt(4, senderCustomerId); // Assuming senderCustomerId is the account number of the sender
	                insertStatement.setInt(5, receiverAccountNumber); // Assuming receiverAccountNumber is the account number of the receiver
	                insertStatement.executeUpdate();
	            }

	            return true; // Transfer successful
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw e; // Re-throw the exception to the caller
	        }
	    }

	    private boolean performCreditDebit( int customerId, String transactionType, double amount)
	            throws SQLException {
	    	 Connection connection = null;
	    	 try {
	    		 connection = DBConnection.connect();
	            // Perform credit/debit transaction updates here

	            // 1. Update customer's balance based on the transaction type (credit or debit)
	            double updateAmount = transactionType.equals("credit") ? amount : -amount;
	            updateBalance( connection, customerId, updateAmount);

	            // 2. Save the credit/debit transaction details in the database
	            String insertTransactionQuery = "INSERT INTO transaction (TransactionType, Date, Amount, SenderAccountNumber, ReceiverAccountNumber) VALUES (?, ?, ?, ?, ?)";
	            try (PreparedStatement insertStatement = connection.prepareStatement(insertTransactionQuery)) {
	                insertStatement.setString(1, transactionType);
	                insertStatement.setDate(2, new java.sql.Date(System.currentTimeMillis())); // Using the current date as the transaction date
	                insertStatement.setDouble(3, amount);
	                insertStatement.setNull(4,Types.INTEGER); // No sender account number for credit/debit transactions
	                insertStatement.setNull(5, Types.INTEGER); // No receiver account number for credit/debit transactions
	                insertStatement.executeUpdate();
	            }

	            return true; // Credit/Debit transaction successful
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw e; // Re-throw the exception to the caller
	        }
	    }
	    private double getAccountBalance(Connection conn, int CustomerId) throws SQLException {
	        String query = "SELECT amount FROM Account WHERE CustomerId = ?";
	        try (PreparedStatement statement = conn.prepareStatement(query)) {
	            statement.setInt(1, CustomerId);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getDouble("amount");
	                }
	            }
	        }
	        return 0; // If customer not found or account balance is zero
	    }

	    private void updateBalance(Connection conn, int CustomerId, double Amount) throws SQLException {
	        String query = "UPDATE account SET Amount = Amount + ? WHERE CustomerId = ?";
	        try (PreparedStatement statement = conn.prepareStatement(query)) {
	            statement.setDouble(1, Amount);
	            statement.setInt(2, CustomerId);
	            statement.executeUpdate();
	        }
	    }
	}

