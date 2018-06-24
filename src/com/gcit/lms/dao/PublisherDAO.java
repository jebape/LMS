package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher>{
	
	public PublisherDAO(Connection conn) {
		super(conn);
	}
	
	public void addPublisher(Publisher Publisher) throws ClassNotFoundException, SQLException {
		save("insert into tbl_Publisher (PublisherName) values (?)", new Object[] { Publisher.getName() });
	}
	
	public void editPublisher(Publisher Publisher) throws ClassNotFoundException, SQLException {
		save("update tbl_Publisher set PublisherName = ? where PublisherId = ?",
				new Object[] { Publisher.getName(), Publisher.getId() });
	}
	
	public void deletePublisher(Publisher Publisher) throws ClassNotFoundException, SQLException {
		save("delete from tbl_Publisher where PublisherId = ?", new Object[] { Publisher.getId() });
	}
	
	public List<Publisher> readAllPublishers() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_Publisher", null);
	}
	
	public List<Publisher> readPublishersByName(String PublisherName) throws ClassNotFoundException, SQLException {
		String searchName = "%"+PublisherName+"%";
		return read("select * from tbl_Publisher where PublisherName like ?", new Object[]{searchName});
	}
	
	public Publisher readPublisherByPK(Integer PublisherId) throws ClassNotFoundException, SQLException {
		
		List<Publisher> Publishers = read("select * from tbl_Publisher where PublisherId = ?", new Object[]{PublisherId});
		if(!Publishers.isEmpty()){
			return Publishers.get(0);
		}
		return null;
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Publisher> Publishers = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Publisher a = new Publisher();
			a.setId(rs.getInt("PublisherId"));
			a.setName(rs.getString("PublisherName"));
			a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (select bookId from tbl_book_Publishers where PublisherId = ?)" , new Object[]{a.getId()}));
			Publishers.add(a);
		}
		return Publishers;
	}

	@Override
	public List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Publisher> Publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher a = new Publisher();
			a.setId(rs.getInt("PublisherId"));
			a.setName(rs.getString("PublisherName"));
			Publishers.add(a);
		}
		return Publishers;
	}

}
