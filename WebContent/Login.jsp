<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body {
            background-color: #f1f1f1;
            font-family: Arial, sans-serif;
            text-align: center;
        }
        
        .login-container {
            display: inline-block;
            background-color: #fff;
            padding: 0 80px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
        }
        
        .login-container h1 {
            margin-bottom: 30px;
        }
        
        .login-container label,
        .login-container input {
            display: block;
            width: 100%;
            margin-bottom: 10px;
        }
        
        .login-container input[type="username"],
        .login-container input[type="password"] {
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
        }
        
       .login-container input[type="submit"] {
             background-color: #4CAF50;
             color: #fff;
             padding: 10px; 
             border: none;
             border-radius: 3px;
            cursor: pointer;
        }
        
        .login-container input[type="submit"]:hover {
            background-color: #45a049;
        }
        
        .login-container input[type="button"] {
            background-color: #ccc;
            color: #000;
            padding: 10px ;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
        
        .login-container input[type="button"]:hover {
            background-color: #999;
        }
    </style>
</head>
<body>
    <div class="login-container">
       <h1>Login</h1>

        <%-- Error messages for invalid login type or missing fields --%>
        <% String error = request.getParameter("error");
           if (error != null) {
             if (error.equals("1")) { %>
              <p style="color: red;">Invalid login type or missing fields</p>
              <% } else if (error.equals("2")) { %>
               <p style="color: red;">Invalid credentials</p>
               <% }
             } %>

        
<%-- Error messages for invalid admin credentials --%>
<% String invalidAdminUsername = (String) request.getAttribute("invalidAdminUsername");
   String invalidAdminPassword = (String) request.getAttribute("invalidAdminPassword");
   if (invalidAdminUsername != null || invalidAdminPassword != null) { %>
   <p style="color: red;">
       <%= invalidAdminUsername != null ? invalidAdminUsername : "" %>
       <%= invalidAdminUsername != null && invalidAdminPassword != null ? " and " : "" %>
       <%= invalidAdminPassword != null ? invalidAdminPassword : "" %>
   </p>
<% } %>

<%-- Error messages for invalid customer credentials --%>
<% String invalidCustomerUsername = (String) request.getAttribute("invalidCustomerUsername");
   String invalidCustomerPassword = (String) request.getAttribute("invalidCustomerPassword");
   if (invalidCustomerUsername != null || invalidCustomerPassword != null) { %>
   <p style="color: red;">
       <%= invalidCustomerUsername != null ? invalidCustomerUsername : "" %>
       <%= invalidCustomerUsername != null && invalidCustomerPassword != null ? " and " : "" %>
       <%= invalidCustomerPassword != null ? invalidCustomerPassword : "" %>
   </p>
<% } %>


        <form action="LoginController" method="post" onsubmit="return validatePassword()">
            <div>
                <label for="loginType">Login Type:</label>
                <select id="loginType" name="loginType" required>
                    <option value="" disabled selected>Select login type</option>
                    <option value="admin">Admin</option>
                    <option value="customer">Customer</option>
                </select>
            </div>
            <div>
                <label for="username">Username:</label>
                <input type="username" id="username" name="username" required>
            </div>
             <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                    
              </div>
            <div>
                <input type="submit" value="Login">
                <input type="button" value="Cancel" onclick="window.location.href='Login.jsp'">
            </div>
    </div>

    <script>
    function validatePassword() {
        var passwordInput = document.getElementById("password").value;

        // Check for at least one uppercase letter
        if (!/[A-Z]/.test(passwordInput)) {
            alert("Password must contain at least one uppercase letter.");
            return false;
        }

        // Check for at least one number
        if (!/\d/.test(passwordInput)) {
            alert("Password must contain at least one number.");
            return false;
        }

        // Check for at least one special character
        if (!/[!@#$%^&*]/.test(passwordInput)) {
            alert("Password must contain at least one special character (!@#$%^&*).");
            return false;
        }

        // Check for minimum length of 10 characters
        if (passwordInput.length < 10) {
            alert("Password must be at least 10 characters long.");
            return false;
        }

        return true;
    }
</script>

    
</body>
</html>
