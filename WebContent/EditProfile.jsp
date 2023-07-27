<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.techlabs.bankapp.Customer" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f1f1f1;
            text-align: center;
            margin: 0;
            padding: 0;
        }

        .header {
            background-color: #4CAF50;
            color: #ffffff;
            padding: 15px;
            text-align: center;
        }

        .container {
            width: 400px;
            margin: 0 auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
        }

        label {
            display: block;
            width: 100%;
            margin-bottom: 10px;
            text-align: left;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        .error-message {
            color: red;
            margin: 5px;
            padding: 2px 10px;
        }

        .button-container {
            margin-top: 20px;
        }

        button[type="submit"] {
            background-color: #4CAF50;
            color: #ffffff;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        a {
            color: #555;
            text-decoration: none;
        }

        .password-message {
            color: #999;
            font-size: 12px;
            margin-top: 5px;
            word-wrap: break-word;
            max-width: 190px;
            text-align: left;
            padding: 0 55px;
        }
    </style>
    <script>
        function validatePassword() {
            var password = document.getElementById("password").value;
            var passwordMessage = document.getElementById("passwordMessage");
            var regex = /^(?=.*[A-Z])(?=.*\d)(?=.*[^a-zA-Z\d]).+$/;

            if (!regex.test(password)) {
                passwordMessage.innerHTML = "Invalid password format. Password must contain at least one uppercase letter, one number, and one special character.";
                return false;
            } else {
                passwordMessage.innerHTML = "";
                return true;
            }
        }

        function validateForm() {
            return validatePassword();
        }
    </script>
</head>
<body>
    <h1>Edit Profile</h1>
    <form action="EditProfileController" method="post">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" value="${loggedInCustomer.firstName}" required><br><br>

        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" value="${loggedInCustomer.lastName}" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>
        <password-message></password-message><br>

        <input type="submit" value="Save">
    </form>

    <script>
        const passwordInput = document.getElementById("password");
        const passwordMessage = document.querySelector("password-message");

        passwordInput.addEventListener("input", () => {
            const isValid = validatePassword(passwordInput.value);
            if (!isValid) {
                passwordMessage.textContent =
                    "Password must contain at least one uppercase letter, one number, and one special character.";
            } else {
                passwordMessage.textContent = "";
            }
        });

        function validatePassword(password) {
            const regex = /^(?=.*[A-Z])(?=.*\d)(?=.*[^a-zA-Z\d]).+$/;
            return regex.test(password);
        }
    </script>
</body>
</html>

