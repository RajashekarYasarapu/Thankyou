package testingcode;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectionExamplee {
	public static String HiringManager;
	public static String AssignedTo;

	  public static void main(String[] args) throws ClassNotFoundException {
		  //Java code for windows authentication mode
		  String url = "jdbc:sqlserver://devsqls0012v1.devfbr.com:1433;;databaseName=COMMON;integratedSecurity=true;trustServerCertificate=true;";

			java.sql.Statement statement = null;
			ResultSet resultSet = null;
			
			// JDBC variables for opening, closing, and managing the connection
			java.sql.Connection connection = null;

			try {
		        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				// Establish the connection
				System.out.println("Trying to connect to devsqls0012v1 server");
				connection = DriverManager.getConnection(url);
				System.out.println("Connection Established sucessfully");
				statement = connection.createStatement();
				int ReferralId = 1086;
			String Query3 ="Select Username from [User] where ID in (8515)";
			
			resultSet = statement.executeQuery(Query3);
			if(resultSet.next()){
				HiringManager = resultSet.getString("Username");
				System.out.println("Hiring manager for this Candidate Referral is: " +HiringManager);
			}

	        }
	        
	        catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Close the connection in the finally block to ensure it's always closed
	            try {
	                if (connection != null && !connection.isClosed()) {
	                    connection.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }	    
}


//Java code for SQL Server authentication mode
/*
 	        // JDBC URL, username, and password of MySQL server
	    //C:\Windows\SysWOW64\SQLServerManager15.msc
	    String url = "jdbc:sqlserver://devsqls0012v1.devfbr.com:1433;databaseName=DealManager;encrypt=false;trustServerCertificate=true;integratedSecurity=true;";
	         String user = "ysatya";
	         String password = "Sy122023!@";
	         
	         java.sql.Statement statement = null;
	         ResultSet resultSet = null;

	        // JDBC variables for opening, closing, and managing the connection
	        java.sql.Connection connection = null;

	        try {
	            // Establish the connection
	        System.out.println("Trying to connect to devsqls0012v1 server");
	        connection = DriverManager.getConnection(url); //, user, password);
	        System.out.println("Connection Established sucessfully");
	        statement = connection.createStatement();

			int ReferralId = 1086;
			String Query3 ="SELECT CONCAT(FNAME, ' ', LNAME) AS Fullname FROM [User]\r\n"
					+ "WHERE Id IN (Select HiringManager from EmployeeReferral where EmployeeReferralId IN ("+ReferralId+"))";
			
			resultSet = statement.executeQuery(Query3);
			if(resultSet.next()){
				HiringManager = resultSet.getString("FullName");
				System.out.println("Hiring manager for this Candidate Referral is: " +HiringManager);
			}
			//Query4 == To get AssignedTo HR for this referral
			String Query4 ="SELECT CONCAT(FNAME, ' ', LNAME) AS Fullname FROM [User]\r\n"
					+ "WHERE Id IN (Select AssignedTo from EmployeeReferral where EmployeeReferralId IN ("+ReferralId+"))";
			
			resultSet = statement.executeQuery(Query4);
			if(resultSet.next()){
				AssignedTo = resultSet.getString("FullName");
				System.out.println("AssignedTo Manager for this Candidate Referral is: " +AssignedTo);
			}
	        }
	        
	        catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Close the connection in the finally block to ensure it's always closed
	            try {
	                if (connection != null && !connection.isClosed()) {
	                    connection.close();
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
 	 */
