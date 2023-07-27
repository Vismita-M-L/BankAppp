<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f1f1f1;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
        }

        h1 {
            text-align: center;
            margin-bottom: 30px;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        li {
            margin-bottom: 10px;
        }

        li a {
            display: block;
            padding: 10px;
            text-decoration: none;
            background-color: #4CAF50;
            color: #fff;
            border-radius: 3px;
        }

        li a:hover {
            background-color: #45a049;
        }
         .cancel-button {
            margin-top: 20px;
        }

        .cancel-button input[type="button"] {
            background-color: #ccc;
            color: #000;
            padding: 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        .cancel-button input[type="button"]:hover {
            background-color: #999;
        }
    </style>
   
</head>
<body>
    <div class="container">
    <% String message = (String) request.getAttribute("message");
           if (message != null) { %>
            <p><%= message %></p>
        <% } %>
        <h1>Welcome Admin!</h1>
        <ul>
            <li><a href="addNewCustomer.jsp">Add New Customer</a></li>
            <li><a href="addBankAccount.jsp">Add Bank Account</a></li>
            <li><a href="viewCustomer.jsp">View Customers</a></li>
            <li><a href="viewTransactions.jsp">View Transactions</a></li>
        </ul>
        <div class="cancel-button">
            <input type="button" value="Back to Login" onclick="window.location.href='Login.jsp'">
        </div>
    </div>
</body>
</html>

