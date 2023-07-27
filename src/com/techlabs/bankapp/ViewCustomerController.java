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


@WebServlet("/ViewCustomerController")
public class ViewCustomerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    
    public ViewCustomerController() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.getRequestDispatcher("/viewCustomer.jsp").forward(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerIdParam = request.getParameter("customerId");

	    if (customerIdParam != null && !customerIdParam.isEmpty()) {
	        try {
	            int CustomerId = Integer.parseInt(customerIdParam);
	            Customer customer = DBConnection.getCustomerById(CustomerId);

	            if (customer != null) {
	                List<Account> accounts = DBConnection.getAccountsByCustomerId(CustomerId);
	                request.setAttribute("customer", customer);
	                request.setAttribute("accounts", accounts);
	            } else {
	                request.setAttribute("error", "Customer not found with ID: " + CustomerId);
	            }
	        } catch (NumberFormatException e) {
	            request.setAttribute("error", "Invalid customer ID format: " + customerIdParam);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    } else {
	        request.setAttribute("error", "Customer ID is not provided.");
	    }

	    request.getRequestDispatcher("/viewCustomer.jsp").forward(request, response);
	}
}


