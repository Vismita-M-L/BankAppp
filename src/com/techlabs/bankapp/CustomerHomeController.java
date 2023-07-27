package com.techlabs.bankapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/CustomerHomeController")
public class CustomerHomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CustomerHomeController() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

        if (action != null) {
            if (action.equals("viewPassbook")) {
                response.sendRedirect("ViewPassbookController");
            } else if (action.equals("newTransaction")) {
                response.sendRedirect("NewTransaction.jsp");
            } else if (action.equals("editProfile")) {
                response.sendRedirect("EditProfile.jsp");
            } else {
                // Handle invalid action
                response.sendRedirect("CustomerHome.jsp");
            }
        } else {
            // Handle invalid action
            response.sendRedirect("CustomerHome.jsp");
        }
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
