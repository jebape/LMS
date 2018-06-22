/**
 * 
 */
package com.gcit.lms.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Jesús Peral
 *
 */
public class ReadAllAuthors {


	private static String driver = "com.mysql.cj.jdbc.Driver";
	// Laptop
	private static String url = "jdbc:mysql://localhost/library?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	// Desktop
	//private static String url = "jdbc:mysql://localhost/library";
	
	private static String username = "root";
	private static String password = "";
	
	
	public static void main(String[] args) {
		
		try {
			//1. Register your driver.
			Class.forName(driver);
			System.out.println("Driver");
			
			//2. Create a Connection
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connection");

			//3. Create a statement object.
			Statement stmt = conn.createStatement();
			System.out.println("Statement");

			//4. Query and Execution.
			String query = "select * from tbl_author";
			System.out.println("Query");

			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				System.out.println("Author ID: "+rs.getInt("authorId"));
				System.out.println("Author Name: "+rs.getString("authorName"));
				System.out.println("-----------------------------------");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
