package com.techlabs.bankapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/EditProfileController")
public class EditProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public EditProfileController() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
		System.out.println("gotrequest");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Customer loggedInCustomer = (Customer) session.getAttribute("loggedInCustomer");

	    // Check if the loggedInCustomer object is null
	    if (loggedInCustomer == null) {
	        // If the loggedInCustomer is null, it means the user is not logged in or the session has expired
	        // Redirect the user to the login page or display an error message
	        response.sendRedirect("Login.jsp?error=login");
	        return;
	    }
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        // Check if any field is edited
        boolean isFirstNameChanged = (firstName != null && !firstName.isEmpty());
        boolean isLastNameChanged = (lastName != null && !lastName.isEmpty());
        boolean isPasswordChanged = (password != null && !password.isEmpty());

        if (!isFirstNameChanged && !isLastNameChanged && !isPasswordChanged) {
            // No changes made, redirect back to the profile page
            response.sendRedirect("EditProfile.jsp");
            return;
        }

        // Check for password format if password is changed
        if (isPasswordChanged && !Customer.isValidPassword(password)) {
            response.sendRedirect("EditProfile.jsp?error=password");
            return;
        }

        // Perform updates in the database if any changes were made
        if (isFirstNameChanged) {
            loggedInCustomer.setFirstName(firstName);
        }
        if (isLastNameChanged) {
            loggedInCustomer.setLastName(lastName);
        }
        if (isPasswordChanged) {
            loggedInCustomer.setPassword(password);
        }

        // Redirect back to the profile page after successful update
        response.sendRedirect("EditProfile.jsp?success=true");
    }
}
	


