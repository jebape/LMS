/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Copy;

/**
 * @author Jesús Peral
 *
 */
public class CopyDAO extends BaseDAO<Copy>{

	public CopyDAO(Connection conn) {
		super(conn);
	}
	
	public void addCopy(Copy copy) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_copies (title) values (?)", new Object[] { copy.getBook() });
	}

	@Override
	public List<Copy> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Copy> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
