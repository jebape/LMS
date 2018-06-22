package com.gcit.lms.dao;

import java.sql.*;
import java.util.*;

public abstract class BaseDAO<T> {


	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/library?useSSL=false";
	private String username = "jb";
	private String password = "ballesteros";
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		return DriverManager.getConnection(url, username, password);
	}
	
	public void save(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		int count = 1;
		for(Object o: vals){
			pstmt.setObject(count, o);
			count++;
		}
		pstmt.executeUpdate();
	}
	
	public List<T> read(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		int count = 1;
		for(Object o: vals){
			pstmt.setObject(count, o);
			count++;
		}
		
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}
	
	public abstract List<T> extractData(ResultSet rs) throws SQLException, ClassNotFoundException;
	
	public List<T> readFirstLevel(String sql, Object[] vals) throws ClassNotFoundException, SQLException{
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		int count = 1;
		for(Object o: vals){
			pstmt.setObject(count, o);
			count++;
		}
		
		ResultSet rs = pstmt.executeQuery();
		return extractDataFirstLevel(rs);
	}
	
	public abstract List<T> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException;
}
