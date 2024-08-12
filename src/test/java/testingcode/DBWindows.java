package testingcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import Configs.AppConfigs;
import io.cucumber.java.en.Given;

public class DBWindows {

@Given("I want to connect to common db")
public void i_want_to_connect_to_common_db() throws ClassNotFoundException {
    // JDBC variables
	java.sql.Statement statement = null;
	ResultSet resultSet = null;
	java.sql.Connection connection = null;
	
	String jdbcurl = AppConfigs.getJDBCurl();
      try {
          // Load the SQL Server JDBC driver
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        System.out.println("Trying to connect");
        // Establish the database connection with Windows authentication
        connection = DriverManager.getConnection(jdbcurl);
        System.out.println("Connected successfully");
		statement = connection.createStatement();

       	String Query = "Select ID  from [User] Where LNAME LIKE '%Yasarapu%'";
		
		resultSet = statement.executeQuery(Query);
		if (resultSet.next()) {
			int UserID = resultSet.getInt("ID");
			System.out.println("User list count is: " + UserID);
		}

       } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
       }
       finally {
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
@Given("coonection close")
public void coonection_close() {

}
}
