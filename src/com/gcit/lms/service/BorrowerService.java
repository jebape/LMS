/**
 * 
 */
package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

/**
 * @author Jesús Peral
 *
 */
public class BorrowerService {
	
	public ConnectionUtil connUtil = new ConnectionUtil();
	
	public void saveAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
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
			conn = connUtil.getConnection();
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

}
