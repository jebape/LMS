package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
/**
 * @author Jesús Peral
 *
 */
public class AuthorDAO extends BaseDAO<Author> {
	
	public AuthorDAO(Connection con) {
		super(con);
	}

	public void addAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("insert into tbl_author (authorName) values (?)", new Object[] { author.getName() });
	}
	
	public void editAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getName(), author.getId() });
	}
	
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("delete from tbl_author where authorId = ?", new Object[] { author.getId() });
	}
	
	public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_author", null);
	}
	
	
	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Author a = new Author();
			a.setId(rs.getInt("authorId"));
			a.setName(rs.getString("authorName"));
			a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)" , new Object[]{a.getId()}));
			authors.add(a);
		}
		return authors;
	}

	@Override
	public List<Author> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author a = new Author();
			a.setId(rs.getInt("authorId"));
			a.setName(rs.getString("authorName"));
			authors.add(a);
		}
		return authors;
	}
	
}
