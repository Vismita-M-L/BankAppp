package com.techlabs.bankapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public LoginController() {
        super();
       
    }
    private boolean validatePassword(String inputPassword, String storedPassword) {
        // Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character
        // Minimum password length is 10 characters
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10,}$";
        return inputPassword.matches(passwordPattern) && inputPassword.equals(storedPassword);
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String loginType = request.getParameter("loginType");
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    if (loginType != null && !loginType.isEmpty() && username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
	        Connection connection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
	        String adminQuery = "SELECT * FROM admin WHERE Username = ?";
	        String customerQuery = "SELECT * FROM customer WHERE Username = ?";

	        try {
	            connection = DBConnection.connect();
	            boolean isValidUser = false;
	            int customerId = 0;
	            if (loginType.equals("admin")) {
	                // Check if the user is an admin
	                preparedStatement = connection.prepareStatement(adminQuery);
	                preparedStatement.setString(1, username);
	                resultSet = preparedStatement.executeQuery();

	                if (resultSet.next()) {
	                    // Validate admin's password
	                    String storedAdminPassword = resultSet.getString("Password");
	                    if (validatePassword(password, storedAdminPassword)) {
	                        isValidUser = true;
	                    } else {
	                        request.setAttribute("invalidAdminPassword", "Invalid admin password");
	                    }
	                } else {
	                    request.setAttribute("invalidAdminUsername", "Invalid admin username");
	                }
	            } else if (loginType.equals("customer")) {
	                // Check if the user is a customer
	                preparedStatement = connection.prepareStatement(customerQuery);
	                preparedStatement.setString(1, username);
	                resultSet = preparedStatement.executeQuery();

	                if (resultSet.next()) {
	                    // Validate customer's password
	                    String storedCustomerPassword = resultSet.getString("Password");
	                    customerId = resultSet.getInt("CustomerId");
	                    if (validatePassword(password, storedCustomerPassword)) {
	                        isValidUser = true;
	                    } else {
	                        request.setAttribute("invalidCustomerPassword", "Invalid customer password");
	                    }
	                } else {
	                    request.setAttribute("invalidCustomerUsername", "Invalid customer username");
	                }
	            } else {
	                // Invalid login type or credentials
	                response.sendRedirect("Login.jsp?error=2");
	                System.out.println("Invalid credentials");
	            }

	            // Close the database resources
	           
	                resultSet.close();
	                preparedStatement.close();
	                connection.close();

	            if (isValidUser) {
	            	 // Set the "customerId" attribute in the session to identify the logged-in customer
	                HttpSession session = request.getSession();
	                session.setAttribute("customerId", customerId);

	            	
	                // Redirect to appropriate home page based on the role
	                if (loginType.equals("admin")) {
	                    // If the user is an admin, set the role attribute in the request and forward to the admin home page
	                    request.setAttribute("role", "admin");
	                    RequestDispatcher dispatcher = request.getRequestDispatcher("AdminHome.jsp");
	                    dispatcher.forward(request, response);
	                    System.out.println("Redirecting to AdminHome.jsp");
	                } else if (loginType.equals("customer")) {
	                    // If the user is a customer, set the role attribute in the request and forward to the customer home page
	                	
	                	request.setAttribute("role", "customer");
	                    RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerHome.jsp");
	                    dispatcher.forward(request, response);
	                    System.out.println("Redirecting to CustomerHome.jsp");
	                }
	            } else {
	                // Invalid credentials (both username and password)
	                response.sendRedirect("Login.jsp?error=2");
	                System.out.println("Invalid credentials");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            
	            
	        }
	    } else {
	        // Invalid login type or missing fields
	        response.sendRedirect("Login.jsp?error=1");
	        System.out.println("Invalid login type or missing fields");
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  doGet(request, response);
        }
}
