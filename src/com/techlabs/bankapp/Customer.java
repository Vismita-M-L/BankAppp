package com.techlabs.bankapp;

public class Customer {
	
        private int customerId;
	    private String username;
	    private String firstName;
	    private String lastName;
	    private String password;

	    
	    // Constructor for adding new customers
	    public Customer(String username, String firstName, String lastName, String password) {
	        this.username = username;
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.password = password;
	    }
	 // Constructor for viewing customer with customerId, firstName, and lastName
	    public Customer(int customerId, String firstName, String lastName) {
	        this.customerId = customerId;
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.username = null;
	        this.password = null;
	    }
	

		public int getCustomerId() {
			return customerId;
		}
		public void setCustomerId(int customerId) {
			this.customerId = customerId;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}	
		public static boolean isValidPassword(String password) {
	        // Password must contain at least one uppercase letter, one number, and one special character
	        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).+$";
	        return password.matches(regex);
	    }
}
