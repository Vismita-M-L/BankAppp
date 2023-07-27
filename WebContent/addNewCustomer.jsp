<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Customer</title>
    <style>
        body {
            background-color: #f1f1f1;
            font-family: Arial, sans-serif;
            text-align: center;
        }

        .form-container {
            display: inline-block;
            background-color: #fff;
            padding: 50px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
        }

        .form-container h1 {
            margin-bottom: 20px;
        }

        .form-container label,
        .form-container input {
            display: block;
            width: 100%;
            margin-bottom: 10px;
        }

        .form-container input[type="text"],
        .form-container input[type="password"] {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        .password-message {
            color: #999;
            font-size: 12px;
            margin-top: 5px;
            word-wrap: break-word;
            max-width: 190px;
             text-align: center;
            padding: 0 55px;
        }
      
         .error-message {
            color: red;
            margin: 5px;
            padding: 2px 10px;
           
        }

        .form-container input[type="submit"] {
            background-color: #4CAF50;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        .form-container input[type="submit"]:hover {
            background-color: #45a049;
        }

        .form-container input[type="button"] {
            background-color: #ccc;
            color: #000;
            padding: 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        .form-container input[type="button"]:hover {
            background-color: #999;
        }
    </style>
</head>
<body>
    <div class="form-container">
    <% String message = (String) request.getAttribute("message");
           if (message != null) { %>
            <p><%= message %></p>
        <% } %>
        <h1>Add New Customer</h1>
       <%-- Display password errors --%>
        <%
            String uppercaseMessage = (String) request.getAttribute("uppercaseMessage");
            String numberMessage = (String) request.getAttribute("numberMessage");
            String specialCharMessage = (String) request.getAttribute("specialCharMessage");

            if (uppercaseMessage != null) {
        %>
            <p class="error-message"><%= uppercaseMessage %></p>
        <%
            }

            if (numberMessage != null) {
        %>
            <p class="error-message"><%= numberMessage %></p>
        <%
            }

            if (specialCharMessage != null) {
        %>
            <p class="error-message"><%= specialCharMessage %></p>
        <%
            }
        %>
        
        <form action="AddNewCustomerController" method="post">
            <div>
                <label for="firstname">First Name:</label>
                <input type="text" id="firstname" name="firstname" required>
            </div>
            <div>
                <label for="lastname">Last Name:</label>
                <input type="text" id="lastname" name="lastname" required>
            </div>
            <div>
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
             <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                <!-- Password requirements message -->
                    <p class="password-message">Password must contain at least one uppercase letter, atleast one number, one special character, and be at least 10 characters long.</p>
            </div>
            <div>
                <input type="submit" value="Add Customer">
                <input type="button" value="Cancel" onclick="window.location.href='AdminHome.jsp'">
            </div>
        </form>
    </div>
    
   

</body>
</html>
