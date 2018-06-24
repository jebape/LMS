package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO<Genre> {
	
	public GenreDAO(Connection con) {
		super(con);
	}

	public void addGenre(Genre Genre) throws ClassNotFoundException, SQLException {
		save("insert into tbl_Genre (GenreName) values (?)", new Object[] { Genre.getName() });
	}
	
	public void editGenre(Genre Genre) throws ClassNotFoundException, SQLException {
		save("update tbl_Genre set GenreName = ? where Genre_id = ?",
				new Object[] { Genre.getName(), Genre.getId() });
	}
	
	public void deleteGenre(Genre Genre) throws ClassNotFoundException, SQLException {
		save("delete from tbl_Genre where Genre_id = ?", new Object[] { Genre.getId() });
	}
	
	public List<Genre> readAllGenres() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_Genre", null);
	}
	
	public List<Genre> readGenresByName(String GenreName) throws ClassNotFoundException, SQLException {
		String searchName = "%"+GenreName+"%";
		return read("select * from tbl_Genre where Genre_name like ?", new Object[]{searchName});
	}
	
	public Genre readGenreByPK(Integer GenreId) throws ClassNotFoundException, SQLException {
		
		List<Genre> Genres = read("select * from tbl_Genre where Genre_id = ?", new Object[]{GenreId});
		if(!Genres.isEmpty()){
			return Genres.get(0);
		}
		return null;
	}
	
	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Genre> Genres = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Genre a = new Genre();
			a.setId(rs.getInt("Genre_Id"));
			a.setName(rs.getString("Genre_Name"));
			a.setBooks(bdao.readFirstLevel("select * from tbl_genre where bookId IN (select bookId from tbl_book_Genres where GenreId = ?)" , new Object[]{a.getId()}));
			Genres.add(a);
		}
		return Genres;
	}

	@Override
	public List<Genre> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Genre> Genres = new ArrayList<>();
		while (rs.next()) {
			Genre a = new Genre();
			a.setId(rs.getInt("Genre_Id"));
			a.setName(rs.getString("Genre_Name"));
			Genres.add(a);
		}
		return Genres;
	}
	
}