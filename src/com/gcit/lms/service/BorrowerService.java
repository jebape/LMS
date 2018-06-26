package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.CopyDAO;
import com.gcit.lms.dao.LoanDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Loan;

/**
 * @author Jesús Peral
 *
 */
public class BorrowerService {
	
	public ConnectionUtil connUtil = new ConnectionUtil();

	
	
	public Borrower getBorrower(String cardNo) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			Borrower borrower = bdao.readBorrowerByID(Integer.parseInt(cardNo));
			conn.commit();
			return borrower;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				conn.rollback();
			}
		} finally{
			conn.close();
		}
		return null;
	}
	
	public List<Branch> getAllBranches() throws SQLException  {
		
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			List<Branch> branches =  bdao.readAllBranches();
			conn.commit();
			return branches;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	
	public List<Book> getAllBooksFromBranch(Integer branchId) throws SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			List<Book> books = bdao.getExistingBooksFromBranch(branchId);
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
	public List<Loan> getAllLoansFromUser(String cardNo) throws NumberFormatException, ClassNotFoundException, SQLException{
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			LoanDAO ldao = new LoanDAO(conn);
			//List<Book> books = bdao.getBooksFrom(Integer.parseInt(cardNo));
			List<Loan> loans = ldao.getLoansBy(Integer.parseInt(cardNo));
			conn.commit();
			return loans;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}
	public void checkOutBook(Book selectedBook, Branch branch, String cardNo) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoanDAO ldao = new LoanDAO(conn);
			CopyDAO cdao = new CopyDAO(conn);
			ldao.checkOutBook(selectedBook, branch, Integer.parseInt(cardNo));
			cdao.updateNoOfCopies(selectedBook.getId(), branch.getId(), cdao.getNumberCopies(selectedBook.getId(), branch.getId())-1);
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public void checkInBook(Book selectedBook, Branch branch, String cardNo) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoanDAO ldao = new LoanDAO(conn);
			CopyDAO cdao = new CopyDAO(conn);
			ldao.checkInBook(selectedBook, branch, Integer.parseInt(cardNo));
			cdao.updateNoOfCopies(selectedBook.getId(), branch.getId(), cdao.getNumberCopies(selectedBook.getId(), branch.getId())+1);

			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

}
