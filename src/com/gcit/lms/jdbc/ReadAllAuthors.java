/**
 * 
 */
package com.gcit.lms.jdbc;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author Mary
 *
 */
public class ReadAllAuthors {


	private static String driver = "com.mysql.jdbc.Driver";
	// Laptop
	private static String url = "jdbc:mysql://localhost:8080/library";
	
	// Desktop
	//private static String url = "jdbc:mysql://localhost/library";
	
	private static String username = "jb";
	private static String password = "ballesteros";
	
	
	public static void main(String[] args) {
		
		try {
			// Register your driver
			Class.forName(driver);
			// Create Connection
			Connection conn = DriverManager.getConnection(url, username, password);
			// create statement
			Statement stmt = conn.createStatement();
			String userInput = null;
			PreparedStatement pstmt = conn.prepareStatement("");
			pstmt.setString(1, userInput);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
