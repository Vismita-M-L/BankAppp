<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>New Transaction</title>
    <style>
        body {
            background-color: #f1f1f1;
            font-family: Arial, sans-serif;
            text-align: center;
        }
        
        .form-container {
            display: inline-block;
            background-color: #fff;
            padding: 20px;
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

        .form-container input[type="number"] {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        .form-container select {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
            width: 100%;
        }

        .form-container input[type="submit"] {
            background-color: #4CAF50;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            width: 100%;
        }

        .form-container input[type="submit"]:hover {
            background-color: #45a049;
        }

        .form-container a {
            display: inline-block;
            padding: 10px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
            background-color: #ccc;
            color: #000;
            border: none;
            border-radius: 3px;
            margin-right: 10px;
        }

        .form-container a:hover {
            background-color: #999;
        }

        .form-container #amountField,
        .form-container #transferFields {
            display: none;
        }

        .form-container #amountField label,
        .form-container #transferFields label {
            text-align: left;
        }

        .form-container #transferFields input[type="text"] {
            width: 100%;
        }

        .form-container #transferFields input[type="text"]:disabled {
            background-color: #f1f1f1;
        }

        /* Styling for the table */
        .form-container table {
            width: 100%;
            border-collapse: collapse;
        }

        .form-container th,
        .form-container td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        .form-container th {
            background-color: #f2f2f2;
        }

        .form-container td input {
            width: 100%;
        }
        
        .tab-container {
            display: none;
        }

        .tab {
            display: inline-block;
            padding: 10px;
            background-color: #f2f2f2;
            border: 1px solid #ddd;
            border-radius: 5px;
            cursor: pointer;
            margin-right: 10px;
        }

        .active-tab {
            background-color: #4CAF50;
            color: #fff;
        }
    </style>
</head>
<body>
    <h1>Welcome, ${customer.firstName} ${customer.lastName}!</h1>
    <p>Account Number: ${customer.accountNumber}</p>
    <p>Balance: ${balance}</p>
    <h2>New Transaction</h2>
    <form action="NewTransactionController" method="post">
    <!-- Display success message if available -->
    <c:if test="${not empty message}">
        <p style="color: green;">${message}</p>
    </c:if>
        <input type="hidden" name="customerId" value="${customer.customerId}">
        <label for="transactionType">Select Transaction Type:</label>
        <select name="transactionType" id="transactionType" required>
            <option value="credit">Credit</option>
            <option value="debit">Debit</option>
            <option value="transfer">Transfer</option>
        </select><br>

        <label for="amount">Amount:</label>
        <input type="number" step="0.01" name="amount" id="amount" required><br>

        <div id="receiverAccountNumberDiv" style="display: none;">
            <label for="receiverAccountNumber">Receiver Account Number:</label>
            <input type="number" name="receiverAccountNumber" id="receiverAccountNumber"><br>
        </div>

        <input type="submit" value="Submit">
        <input type="button" value="Cancel" onclick="window.location.href='CustomerHome.jsp'">
    </form>
    <script>
        const transactionTypeSelect = document.getElementById("transactionType");
        const receiverAccountNumberDiv = document.getElementById("receiverAccountNumberDiv");

        transactionTypeSelect.addEventListener("change", function () {
            if (transactionTypeSelect.value === "transfer") {
                receiverAccountNumberDiv.style.display = "block";
            } else {
                receiverAccountNumberDiv.style.display = "none";
            }
        });
    </script>
</body>
</html>
