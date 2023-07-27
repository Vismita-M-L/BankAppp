<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <style>
      
        body {
            font-family: Arial, sans-serif;
            background-color: #f1f1f1;
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }

        tr:hover {
            background-color: #f2f2f2;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
        }

        .footer {
            text-align: center;
            margin-top: 20px;
        }
        .center {
            text-align: center;
            margin-top: 20px;
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
    <h1>Passbook Details</h1>
    <table border="1">
        <tr>
            <th>Transaction ID</th>
            <th>Transaction Type</th>
            <th>Date</th>
            <th>Amount</th>
            <th>Sender's Account Number</th>
            <th>Receiver's Account Number</th>
        </tr>

        <c:forEach items="${transactions}" var="entry">
            <tr>
                <td>${entry.transactionId}</td>
                <td>${entry.transactionType}</td>
                <td>${entry.date}</td>
                <td>${entry.amount}</td>
                <td>${entry.senderAccountNumber}</td>
                <td>${entry.receiverAccountNumber}</td>
            </tr>
        </c:forEach>
    </table>


    <br>
    <a href="CustomerHome.jsp">Back to Customer Home</a>
</body>
</html>







