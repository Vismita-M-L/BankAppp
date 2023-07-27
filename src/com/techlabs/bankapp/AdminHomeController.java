package com.techlabs.bankapp;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminHomeController")
public class AdminHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public AdminHomeController() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

	    if (action != null) {
	        switch (action) {
	            case "addNewCustomer":
	                // Redirect to Add New Customer page
	                RequestDispatcher addNewCustomerDispatcher = request.getRequestDispatcher("/addNewCustomer.jsp");
	                addNewCustomerDispatcher.forward(request, response);
	                break;
	            case "addBankAccount":
	                // Redirect to Add Bank Account page
	                RequestDispatcher addBankAccountDispatcher = request.getRequestDispatcher("/addBankAccount.jsp");
	                addBankAccountDispatcher.forward(request, response);
	                break;
	            case "viewCustomers":
	                // Forward to the viewCustomer.jsp
	                RequestDispatcher viewCustomerDispatcher = request.getRequestDispatcher("/viewCustomer.jsp");
	                viewCustomerDispatcher.forward(request, response);
	                break;
	            case "viewTransactions":
	                // Redirect to View Transactions page
	                RequestDispatcher viewTransactionsDispatcher = request.getRequestDispatcher("/viewTransactions.jsp");
	                viewTransactionsDispatcher.forward(request, response);
	                break;
	            default:
	                // Invalid action, redirect to Admin Home page
	                response.sendRedirect("adminHome.jsp");
	                break;
	        }
	    } else {
	        // No action specified, redirect to Admin Home page
	        response.sendRedirect("adminHome.jsp");
	    }
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
