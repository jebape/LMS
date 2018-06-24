package com.gcit.lms.service;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.CopyDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Copy;
import com.gcit.lms.entity.Branch;

public class LibrarianService {
	ConnectionUtil cUtil = new ConnectionUtil();

	public List<Branch> getAllBranches() throws SQLException, ClassNotFoundException {
		
		Connection conn = cUtil.getConnection();
		try {
			BranchDAO bdao = new BranchDAO(conn);
			return bdao.readAllBranches();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	
}
