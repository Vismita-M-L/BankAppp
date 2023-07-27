package com.techlabs.bankapp;

import java.io.IOException;
import java.sql.Connection;
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
import javax.servlet.http.HttpSession;


@WebServlet("/ViewPassbookController")
public class ViewPassbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ViewPassbookController() {
        super();
    
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        int customerId = -1; // Default value for no logged-in customer

        // Check if a customer is logged in
        if (session.getAttribute("customerId") != null) {
            customerId = (int) session.getAttribute("customerId");
        }

        if (customerId != -1) {
            // Handle logged-in customer case
            List<Transaction> transactions = DBConnection.getPassbookEntriesForCustomer(customerId);
            request.setAttribute("transactions", transactions);
            request.getRequestDispatcher("Passbook.jsp").forward(request, response);
        } else {
            // No valid customer login, redirect to login page
            response.sendRedirect("Login.jsp");
        }
    }
    

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
