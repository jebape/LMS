/**
 * 
 */
package com.gcit.lms.service;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Jesús Peral
 *
 */
public class ConnectionUtil {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	// Laptop
	// private static String url =
	// "jdbc:mysql://localhost/library?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	// private static String username = "root";
	// private static String password = "";

	// Desktop
	private static String url = "jdbc:mysql://localhost/library?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	private static String username = "root";
	private static String password = "ballesteros";

	public Connection getConnection() throws SQLException, ClassNotFoundException {

		/*
		 * Properties prop = new Properties(); InputStream input = null; Connection conn
		 * = null;
		 * 
		 * try { input = new FileInputStream("mysql.properties"); prop.load(input);
		 * Class.forName(prop.getProperty("driver")); conn =
		 * DriverManager.getConnection(prop.getProperty("url"),
		 * prop.getProperty("username"), prop.getProperty("password"));
		 * conn.setAutoCommit(Boolean.FALSE); }catch(IOException e) {
		 * e.printStackTrace(); }
		 */

		Class.forName(driver);

		Connection conn = DriverManager.getConnection(url, username, password);
		//conn.setAutoCommit(Boolean.FALSE);
		
		return conn;
	}
}
