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
		
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
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
	
	public Integer getNumberCopiesFrom(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			CopyDAO bdao = new CopyDAO(conn);
			return bdao.getNumberCopies(bookId, branchId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return 0;
	}
	
	public void updateNoOfCopies(Integer bookId, Integer branchId, Integer numbercopies) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			CopyDAO bdao = new CopyDAO(conn);
			bdao.updateNoOfCopies(bookId, branchId, numbercopies);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public void updateBranchByID(Integer id, String name, String address) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			Branch newBranch = bdao.readBranchByPK(id);
			if(!"N/A".equalsIgnoreCase(name))		newBranch.setName(name);
			else	newBranch.setName(newBranch.getName());
			if(!"N/A".equalsIgnoreCase(address))	newBranch.setAddress(address);
			else	newBranch.setAddress(newBranch.getAddress());

			bdao.updateBranch(newBranch);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	public List<Book> getAllBooksFromBranch(Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BranchDAO lbdao = new BranchDAO(conn);
			return lbdao.getAllBooksFromBranch(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			if(conn!=null){
				conn.close();
			}
		}
		return null;
	}

	
}
