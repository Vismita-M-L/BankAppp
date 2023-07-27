<!DOCTYPE html>
<html>
<head>
    <title>Customer Home</title>
    <style>
        body {
            background-color: #f1f1f1;
            font-family: Arial, sans-serif;
            text-align: center;
        }

        .container {
            width: 400px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        h1 {
            margin-bottom: 20px;
        }

        ul {
            list-style: none;
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
            border: none;
            border-radius: 5px;
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
        <h1>Welcome, Customer!</h1>
        <%-- Check if the transactionSuccess attribute exists and display the message --%>
        <% if (request.getAttribute("transactionSuccess") != null) { %>
            <p><%= request.getAttribute("transactionSuccess") %></p>
        <% } %>
        <ul>
            <li><a href="Passbook.jsp">Passbook</a></li>
            <li><a href="NewTransaction.jsp">New Transaction</a></li>
            <li><a href="EditProfile.jsp">Edit Profile</a></li>
        </ul>
        <br>
        <div class="cancel-button">
            <input type="button" value="Back to Login" onclick="window.location.href='Login.jsp'">
        </div>
    </div>
</body>
</html>
