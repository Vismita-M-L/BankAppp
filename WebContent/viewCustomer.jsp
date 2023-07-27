<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Customer</title>
   <style>
        body {
            background-color: #f1f1f1;
            font-family: Arial, sans-serif;
            text-align: center;
        }

        .container {
            display: inline-block;
            background-color: #fff;
            padding: 55px;
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
            margin-bottom: 10px;
        }

        .container input[type="number"] {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ccc;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #e5e5e5;
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
        <h1>View Customer</h1>
        <form action="ViewCustomerController" method="post">
            <label for="customerId">Enter Customer ID:</label>
            <input type="number" name="customerId" required>
            <input type="submit" value="View Details">
        </form>

        <c:if test="${not empty customer}">
            <table>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Account Number</th>
                    <th>Amount</th>
                </tr>
                <tr>
                    <td>${customer.firstName}</td>
                    <td>${customer.lastName}</td>
                    <td>${accounts[0].accountNumber}</td>
                    <td>${accounts[0].amount}</td>
                </tr>

                <c:forEach var="account" items="${accounts}" varStatus="loop">
                    <c:if test="${loop.index > 0}">
                        <tr>
                            <td></td>
                            <td></td>
                            <td>${account.accountNumber}</td>
                            <td>${account.amount}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </c:if>

         <c:if test="${empty accounts}">
            <p>No accounts found for this customer.</p>
        </c:if>

        <div class="cancel-button">
            <input type="button" value="Cancel" onclick="window.location.href='AdminHome.jsp'">
        </div>
    </div>
</body>
</html>

