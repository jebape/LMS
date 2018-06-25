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

/**
 * @author Jesús Peral
 *
 */
public class LibrarianService {
	ConnectionUtil connUtil = new ConnectionUtil();

	public List<Branch> getAllBranches() throws SQLException, ClassNotFoundException {
		
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			List<Branch> branches = bdao.readAllBranches();
			conn.commit();
			return branches;
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
			conn = connUtil.getConnection();
			CopyDAO bdao = new CopyDAO(conn);
			Integer nCopies = bdao.getNumberCopies(bookId, branchId);
			conn.commit();
			return nCopies;
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
			conn = connUtil.getConnection();
			CopyDAO bdao = new CopyDAO(conn);
			bdao.updateNoOfCopies(bookId, branchId, numbercopies);
			conn.commit();

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
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			Branch newBranch = bdao.readBranchById(id);
			if(!"N/A".equalsIgnoreCase(name))		newBranch.setName(name);
			else	newBranch.setName(newBranch.getName());
			if(!"N/A".equalsIgnoreCase(address))	newBranch.setAddress(address);
			else	newBranch.setAddress(newBranch.getAddress());

			bdao.updateBranch(newBranch);
			conn.commit();

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
			conn = connUtil.getConnection();
			BranchDAO lbdao = new BranchDAO(conn);
			List<Book> books = lbdao.getAllBooksFromBranch(branchId);	
			conn.commit();
			return books;
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
