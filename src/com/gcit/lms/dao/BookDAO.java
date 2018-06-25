package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;

public class BookDAO extends BaseDAO<Book>{
	public BookDAO(Connection connection) {
		super(connection);
	}

	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book (title) values (?)", new Object[] { book.getTitle() });
	}
	
	public void addBookAuthors(Integer authorId, Integer bookId) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_authors values (?, ?)", new Object[] { bookId, authorId});
	}
	
	public void addBookGenres(Integer bookId, Integer genreId) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book_genres values (?, ?)", new Object[] {genreId, bookId});
	}
	
	public void addBookPublisher(Integer bookId, Integer pubId) throws ClassNotFoundException, SQLException{
		save("update tbl_book set pubId = ? where bookId = ?", new Object[] {pubId, bookId});
	}
	
	public Integer addBookWithID(Book book) throws ClassNotFoundException, SQLException {
		return saveWithID("insert into tbl_book (title) values (?)", new Object[] { book.getTitle() });
	}

	public void editBook(Book book) throws ClassNotFoundException, SQLException {
		save("update tbl_book set title = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getId() });
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book where bookId = ?", new Object[] { book.getId() });
	}
	
	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book", null);
	}
	
	
	public List<Book> getBooksFrom(Integer cardNo) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book where bookId in (select bookId from tbl_book_loans where cardNo = ?)", new Object[]{cardNo});
	}
	
	public Book readBookById(Integer bookId) throws ClassNotFoundException, SQLException {
		
		List<Book> books = read("select * from tbl_book where bookId = ?", new Object[]{bookId});
		if(!books.isEmpty()){
			return books.get(0);
		}
		return null;
	}

	public List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		GenreDAO gdao = new GenreDAO(conn);
		PublisherDAO pdao = new PublisherDAO(conn);
		while (rs.next()) {
			Book b = new Book();
			b.setId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setPublisher(pdao.readPublisherById(rs.getInt("pubId")));
			b.setAuthors(adao.readFirstLevel("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[]{b.getId()}));
			b.setGenres(gdao.readFirstLevel("select * from tbl_genre where genre_Id IN (select genre_Id from tbl_book_genres where bookId = ?)", new Object[]{b.getId()}));
			books.add(b);
		}
		return books;
	}
	
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book b = new Book();
			b.setId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}
		return books;
	}

	public List<Book> getExistingBooksFromBranch(Integer branchId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book where bookId in (select bookId from tbl_book_copies where branchId = ? and noOfCopies > 0)", new Object[]{branchId});
	}
}
