package com.techlabs.bankapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AddNewCustomerController")
public class AddNewCustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public AddNewCustomerController() {
        super();
        
    }
  

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/addNewCustomer.jsp").forward(request, response);
	    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String password = request.getParameter("password");

        // Validate password on the server-side
        String passwordErrorMessage = validatePassword(password);
        if (!passwordErrorMessage.isEmpty()) {
            String uppercaseMessage = "Uppercase letter is required.";
            String numberMessage = "Number is required.";
            String specialCharMessage = "Special character is required.";

            if (!password.matches(".*[A-Z].*")) {
                request.setAttribute("uppercaseMessage", uppercaseMessage);
            }

            if (!password.matches(".*\\d.*")) {
                request.setAttribute("numberMessage", numberMessage);
            }

            if (!password.matches(".*[!@#$%^&*].*")) {
                request.setAttribute("specialCharMessage", specialCharMessage);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("addNewCustomer.jsp");
            dispatcher.forward(request, response);
            return;
        }
           
        
	    
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        connection = DBConnection.connect();
	        System.out.println("Connection established successfully");

	        // Check if customer already exists
	        String checkQuery = "SELECT COUNT(*) FROM customer WHERE firstname = ? AND lastname = ?";
	        preparedStatement = connection.prepareStatement(checkQuery);
	        preparedStatement.setString(1, firstName);
	        preparedStatement.setString(2, lastName);
	        resultSet = preparedStatement.executeQuery();
	        resultSet.next();
	        int customerCount = resultSet.getInt(1);

	        if (customerCount > 0) {
	            // Customer already exists
	            System.out.println("Customer already exists");
	            request.setAttribute("message", "Customer already exists");
	        } else {
	            // Insert new customer into the database
	            String insertQuery = "INSERT INTO customer (username, firstname, lastname, password) VALUES (?, ?, ?, ?)";
	            preparedStatement = connection.prepareStatement(insertQuery);
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, firstName);
	            preparedStatement.setString(3, lastName);
	            preparedStatement.setString(4, password);
	            preparedStatement.executeUpdate();

	            System.out.println("New customer added successfully");
	            request.setAttribute("message", "New customer added successfully");
	        }

	        // Close the database resources
	        resultSet.close();
	        preparedStatement.close();
	        connection.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    request.setAttribute("message", "New customer added successfully");

        // Forward the request to the AddNewCustomer.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("addNewCustomer.jsp");
        dispatcher.forward(request, response);
    }

    // Server-side password validation method
    private String validatePassword(String password) {
        StringBuilder errorMessage = new StringBuilder();

        // Check for at least one uppercase letter
        if (!password.matches(".*[A-Z].*")) {
            errorMessage.append("Uppercase letter is required. ");
        }

        // Check for at least one number
        if (!password.matches(".*\\d.*")) {
            errorMessage.append("Number is required. ");
        }

        // Check for at least one special character
        if (!password.matches(".*[!@#$%^&*].*")) {
            errorMessage.append("Special character is required. ");
        }

        return errorMessage.toString();
    }
}
    
   






	

