package com.techlabs.bankapp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBConnection {
	private static DBConnection Dbconnection;
    private static Connection connection;
    private static int AccountNumber;
    
    DBConnection() {
    }
    
    public static synchronized DBConnection getDBConnection() {
        if ( Dbconnection == null) {
        	 Dbconnection = new DBConnection();
        }
        return  Dbconnection;
    }
    
    public static Connection connect() throws SQLException {
        try {
            // Registering the drivers
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb", "root", "vismita2000");
            System.out.println("Connection established successfully");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Failed to establish connection to the database.");
        }
        return connection;
    }

    public void createbankDb() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE DATABASE IF NOT EXISTS bankdb");
            preparedStatement.executeUpdate();
            System.out.println("bankdb created successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }
    
    public void createAdminTable() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Admin (" +
                    "AdminId INT PRIMARY KEY," +
                    "UserName VARCHAR(15)," +
                    "Password VARCHAR(10)" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("Admin Table created successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }


    public void createCustomerTable() throws SQLException {
        connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Customer (" +
                    "CustomerId INT  AUTO_INCREMENT PRIMARY KEY," +
                    "Username VARCHAR(20)," +
                    "FirstName VARCHAR(15)," +
                    "LastName VARCHAR(15)," +
                    "Password VARCHAR(10)" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("Customer Table created successfully");
            preparedStatement.close();
        } finally {
            connection.close();
        }
    }

      public void createAccountTable() throws SQLException {
        connect();
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Account (" +
                    "AccountNumber INT PRIMARY KEY," +
                    "Amount DECIMAL(10,2)," +
                    "CustomerId INT AUTO_INCREMENT,"+
                    "FOREIGN KEY(CustomerId ) REFERENCES Customer(CustomerId )" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("Account Table created successfully");
            preparedStatement.close();
            connection.close();
    }
      public void createTransactionTable() throws SQLException {
    	    connect();
    	        PreparedStatement preparedStatement = connection.prepareStatement(
    	            "CREATE TABLE IF NOT EXISTS Transaction (" +
    	            "TransactionId INT AUTO_INCREMENT PRIMARY KEY, " +
    	            "TransactionType VARCHAR(10), " +
    	            "Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
    	            "AMOUNT INT, " +
    	            "SenderAccountNumber INT, " +
    	            "ReceiverAccountNumber INT, " +
    	            "FOREIGN KEY (SenderAccountNumber) REFERENCES Account(AccountNumber), " +
    	            "FOREIGN KEY (ReceiverAccountNumber) REFERENCES Account(AccountNumber)" +
    	            ")"
    	        );
    	        preparedStatement.executeUpdate();
    	        System.out.println("Transaction Table created successfully");
    	        preparedStatement.close();
    	        connection.close();
    	    }


    public void createCustomerTransactionTable() throws SQLException {
        connect();
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS CustomerTransaction (" +
                   "CustomerId INT  AUTO_iNCREMENT," +
                    "TransactionId INT  ," +
                    "PRIMARY KEY (CustomerId, TransactionId),"+
                    "FOREIGN KEY (CustomerId ) REFERENCES Customer(CustomerId )," +
                    "FOREIGN KEY (TransactionId) REFERENCES Transaction(TransactionId)" +
                    ")");
            preparedStatement.executeUpdate();
            System.out.println("CustomerTransaction Table created successfully");
            preparedStatement.close();
            connection.close();    
    }
    public void addAdminDetails(int adminId, String Username, String password) throws SQLException {
    	connect();
    	PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Admin (AdminId, Username, Password) VALUES (?, ?, ?)");
    	preparedStatement.setInt(1, adminId);
    	preparedStatement.setString(2, Username);
    	preparedStatement.setString(3, password);
    	preparedStatement.executeUpdate();
    	System.out.println("Admin details added successfully");
    	preparedStatement.close();
    	connection.close();

        } 
    
    public static void addCustomerDetails(String username, String firstName, String lastName, String password) throws SQLException {
        connect();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer (Username, FirstName, LastName, Password) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();
            System.out.println("Customer details added successfully");
            preparedStatement.close();
            connection.close();
        } 

    public static int addAccountDetails(int customerId, double amount) throws SQLException {
        connect();
        int currentAccountNumber = generateRandomAccountNumber(); // Generate a random 8-digit account number

        // Check if the generated account number already exists for the entered customer ID
        while (isAccountNumberExists(customerId, currentAccountNumber)) {
            currentAccountNumber = generateRandomAccountNumber(); // Generate a new account number
        }

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Account (AccountNumber, Amount, CustomerId) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, currentAccountNumber);
        preparedStatement.setDouble(2, amount);
        preparedStatement.setInt(3, customerId);
        preparedStatement.executeUpdate();
        System.out.println("Account details added successfully. Account Number: " + currentAccountNumber);
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            currentAccountNumber = rs.getInt(1); // Retrieve the generated account number
        }
        preparedStatement.close();
        connection.close();
        return currentAccountNumber;
    }

    private static boolean isAccountNumberExists(int customerId, int accountNumber) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Account WHERE CustomerId = ? AND AccountNumber = ?");
        preparedStatement.setInt(1, customerId);
        preparedStatement.setInt(2, accountNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        preparedStatement.close();
        connection.close();
        return count > 0;
    }

    public static int generateRandomAccountNumber() {
        Random random = new Random();
        return 10000000 + random.nextInt(90000000); // Generate an 8-digit account number
    }

    public void addTransactionDetails(String transactionType, Date date, int Amount, int senderAccountNumber, int receiverAccountNumber) throws SQLException {
        connect();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Transaction (TransactionType, Date, Amount, SenderAccountNumber, ReceiverAccountNumber) VALUES (?, ?, ?, ?, ?)");
        preparedStatement.setString(1, transactionType);
        preparedStatement.setDate(2, date);
        preparedStatement.setInt(3,Amount);
        preparedStatement.setInt(4, senderAccountNumber);
        preparedStatement.setInt(5, receiverAccountNumber);
        preparedStatement.executeUpdate();
        System.out.println("Transaction details added successfully");
        preparedStatement.close();
        connection.close();
    }


    public void addCustomerTransactionDetails( int transactionId) throws SQLException {
      connect();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CustomerTransaction ( TransactionId) VALUES ( ?)");
            preparedStatement.setInt(1, transactionId);
            preparedStatement.executeUpdate();
            System.out.println("CustomerTransaction details added successfully");
            preparedStatement.close();
            connection.close();
    } 
    
    public static void deleteCustomer(int customerId) throws SQLException {
     	connect();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE CustomerId = ?");
        preparedStatement.setInt(1, customerId);
        
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Customer details deleted successfully");
        } else {
            System.out.println("Failed to delete customer details");
        }
        preparedStatement.close();
        connection.close();
    }
    public static void deleteAccountDetails(int accountNumber) throws SQLException {
            connect();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM account WHERE AccountNumber = ?");
            preparedStatement.setInt(1, accountNumber);
            int rowsAffected = preparedStatement.executeUpdate();
            preparedStatement.close(); 
            connection.close();
    }
  
    public static Customer getCustomerById(int CustomerId) throws SQLException {
        Customer customer = null;
        
        connect();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE CustomerId = ?");
            preparedStatement.setInt(1, CustomerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String FirstName = resultSet.getString("firstname");
                String LastName = resultSet.getString("lastname");
                customer = new Customer(CustomerId, FirstName, LastName);
            }
            preparedStatement.close();
            connection.close();

        return customer;
    }
    public static CustomerAccount getCustomerAccountById(int customerId) throws SQLException {
        connect();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE CustomerId = ?");
        preparedStatement.setInt(1, customerId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String username = resultSet.getString("username");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");

            // Get accounts associated with the customer
            List<Account> accounts = getAccountsByCustomerId(customerId);

            return new CustomerAccount(customerId, username, firstName, lastName, accounts);
        }

        return null;
    }


    public static List<Account> getAccountsByCustomerId(int CustomerId) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        connect();         
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account WHERE CustomerId = ?");
            preparedStatement.setInt(1, CustomerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	int AccountId=resultSet.getInt("accountId");
                int AccountNumber = resultSet.getInt("accountNumber");
                double Amount = resultSet.getDouble("amount");
                Account account = new Account(AccountId, AccountNumber, Amount);
                accounts.add(account);
            }
            preparedStatement.close();
            connection.close();
        return accounts;
    }

    public static List<Transaction> getTransactionsForCustomer(int CustomerId) throws SQLException {
    	List<Transaction> transactions = new ArrayList<>();
        connect();       
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT t.TransactionId, t.TransactionType, t.Date, t.Amount, t.SenderAccountNumber, t.ReceiverAccountNumber "
                + "FROM transaction t "
                + "INNER JOIN customertransaction ct ON t.TransactionId = ct.TransactionId "
                + "WHERE ct.CustomerId = ?"
        );
        preparedStatement.setInt(1, CustomerId);

        ResultSet resultSet =  preparedStatement.executeQuery();
        while (resultSet.next()) {
        	int transactionId=resultSet.getInt("transactionId");
            String transactionType = resultSet.getString("transactionType");
            Date date = resultSet.getDate("date");
            double amount = resultSet.getDouble("amount");
            int senderAccountNumber = resultSet.getInt("senderAccountNumber");
            int receiverAccountNumber = resultSet.getInt("receiverAccountNumber");

            Transaction transaction = new Transaction( );
            transactions.add(transaction);
        } 

        preparedStatement.close();
        connection.close();
        return transactions;
    }
    public static void updateCustomer(Customer customer) throws SQLException {
        connect();

        PreparedStatement preparedStatement = connection.prepareStatement(
            "UPDATE customer SET FirstName = ?, LastName = ?, Password = ? WHERE CustomerId = ?"
        );
        preparedStatement.setString(1, customer.getFirstName());
        preparedStatement.setString(2, customer.getLastName());
        preparedStatement.setString(3, customer.getPassword());
        preparedStatement.setInt(4, customer.getCustomerId());

        preparedStatement.executeUpdate();
        
        preparedStatement.close();
        connection.close();
    }
  

    public List<Transaction> getPassbookEntriesForCustomer(int CustomerId) {
        List<Transaction> transactions= new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Create a database connection
        	 connection = DBConnection.connect();

            // Prepare the SQL query
        	 String query = "SELECT t.TransactionId, t.TransactionType, t.Date, t.Amount, " +
                     "t.SenderAccountNumber, t.ReceiverAccountNumber " +
                     "FROM transaction t " +
                     "JOIN account a ON t.SenderAccountNumber = a.AccountNumber " +
                     "WHERE a.CustomerId = ?";
             statement = connection.prepareStatement(query);
             statement.setInt(1, CustomerId);

            // Execute the query and get the result set
            resultSet = statement.executeQuery();

            // Process the result set and populate the PassbookEntry list
            while (resultSet.next()) {
                Transaction entry = new Transaction();
                entry.setTransactionId(resultSet.getInt("TansactionId"));
                entry.setSenderAccountNumber(resultSet.getInt("SenderAccountNumber"));
                entry.setReceiverAccountNumber(resultSet.getInt("ReceiverAccountNumber"));
                entry.setTransactionType(resultSet.getString("TransactionType"));
                entry.setAmount(resultSet.getDouble("Amount"));
                entry.setDate(resultSet.getDate("Date"));
                transactions.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return transactions;
    }
    
}
    

    
  


