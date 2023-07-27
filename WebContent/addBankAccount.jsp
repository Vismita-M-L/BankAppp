<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Bank Account</title>
    <style>
        body {
            background-color: #f1f1f1;
            font-family: Arial, sans-serif;
            text-align: center;
        }

        .container {
            display: inline-block;
            background-color: #fff;
            padding: 50px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
        }

        .container h1 {
            margin-bottom: 20px;
        }

        .container label,
        .container input {
            display: block;
            width: 100%;
            margin-bottom: 20px;
        }
        .container input[type="number"],
        .container input[type="text"] {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
            width: 100%;
       }
      
        .container input[type="submit"] {
            background-color: #4CAF50;
            color: #fff;
            padding: 10px; 
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        .container input[type="submit"]:hover {
            background-color: #45a049;
        }

        .container input[type="button"] {
            background-color: #ccc;
            color: #000;
            padding: 10px ;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        .container input[type="button"]:hover {
            background-color: #999;
        }
    </style>
</head>
<body>
    <div class="container">
        <% String message = (String) request.getAttribute("message");
           if (message != null) { %>
            <p style="color: black;"><%= message %></p>
        <% } %>
        <h1>Add Bank Account</h1>
        <form action="AddBankAccountController" method="post">
            <div>
                <label for="customerId">Customer ID:</label>
                <input type="number" id="customerId" name="customerId" required>
               
                
                <!-- Display customer name if it exists in the request attribute -->
                <% String customerName = (String) request.getAttribute("customerName");
                   if (customerName != null) { %>
                    <p>Customer Name: <%= customerName %></p>
                <% } %>
            
                <label for="amount">Initial Amount:</label>
                <input type="number" id="amount" name="amount" required>
            </div>
            <div>
                <label for="generateAccountNumber">Generate Account Number:</label>
                <input type="checkbox" id="generateAccountNumber" name="generateAccountNumber" >
               
                
            </div>
            <div>
                <input type="submit" value="Submit">
                <input type="button" value="Cancel" onclick="window.location.href='AdminHome.jsp'">
            </div>
        </form>
    </div>
    
</body>
</html>

