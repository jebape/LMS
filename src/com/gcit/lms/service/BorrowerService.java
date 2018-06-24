/**
 * 
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Genre;

/**
 * @author Jesús Peral
 *
 */
public class BorrowerService {
	
	public ConnectionUtil cUtil = new ConnectionUtil();
	
	public void saveAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			if(author.getId() != null && author.getName() != null) {
				adao.editAuthor(author);
			}else if(author.getId() != null) {
				adao.deleteAuthor(author);
			}else {
				adao.addAuthor(author);
			}
			conn.commit();
			System.out.println("Author added successfully");
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				conn.rollback();
			}
		}finally {
			conn.close();
		}
	}
	
	public void saveBook(Book book) throws SQLException{
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			Integer bookId = bdao.addBookWithID(book);
			for(Author a: book.getAuthors()){
				bdao.addBookAuthors(a.getId(), bookId);
			}
			for(Genre g: book.getGenres()){
				bdao.addBookGenres(g.getId(), bookId);
			}
			//TODO Rest of the entities.
			conn.commit();
			System.out.println("Book added sucessfully");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				conn.rollback();
			}
		} finally{
			conn.close();
		}
		
	}
	
	public Borrower getBorrower(String cardNo) throws SQLException {
		Connection conn = null;
		try {
			conn = cUtil.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			return bdao.readBorrowerByID(Integer.parseInt(cardNo));
			
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
			conn = cUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			return bdao.readAllBranches();

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
