package com.techlabs.bankapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AddBankAccountController")
public class AddBankAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public AddBankAccountController() {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/addBankAccount.jsp").forward(request, response);
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerIdStr = request.getParameter("customerId");

        // Check if customerIdStr is not empty or null
        if (customerIdStr == null || customerIdStr.trim().isEmpty()) {
            // Set an error message and forward the request back to the JSP
            request.setAttribute("message", "Customer ID is required.");
            request.getRequestDispatcher("/addBankAccount.jsp").forward(request, response);
            return;
        }

        try {
            int customerId = Integer.parseInt(customerIdStr);

            // Retrieve the customer details by their ID (you need to implement this logic)
            Customer customer = DBConnection.getCustomerById(customerId);

            // Check if the customer exists
            if (customer != null) {
                // Store the customer's name in a request attribute to display it in the JSP
                String customerName = customer.getFirstName() + " " + customer.getLastName();
                request.setAttribute("customerName", customerName);
                
                // Get the entered amount
                double amount = Double.parseDouble(request.getParameter("amount"));
                
                // Call the method to add the bank account details
                int accountNumber = DBConnection.addAccountDetails(customerId, amount);
                
                // Set success message if bank account added successfully
                request.setAttribute("message", " Bank AccountNumber  added successfully" );
            } else {
                // If the customer does not exist, set an error message
                request.setAttribute("message", "Customer not found");
            }
        } catch (NumberFormatException e) {
            // If the customer ID or amount cannot be parsed as numbers, set an error message
            request.setAttribute("message", "Invalid customer ID or amount.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Failed to add bank account");
        }

        // Forward the request back to the "Add Bank Account" form with the result message
        request.getRequestDispatcher("/addBankAccount.jsp").forward(request, response);
    }
}
