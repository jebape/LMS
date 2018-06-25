package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Publisher;
/**
 * @author Jesús Peral
 *
 */
public class PublisherDAO extends BaseDAO<Publisher>{
	
	public PublisherDAO(Connection conn) {
		super(conn);
	}
	
	public void addPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?,?,?)", new Object[] { publisher.getName(), publisher.getAddress(),publisher.getPhone() });
	}
	
	public void editPublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
				new Object[] { publisher.getName(), publisher.getAddress(),publisher.getPhone(), publisher.getId() });
	}
	
	public void deletePublisher(Publisher publisher) throws ClassNotFoundException, SQLException {
		save("delete from tbl_publisher where publisherId = ?", new Object[] { publisher.getId() });
	}
	
	public List<Publisher> readAllPublishers() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_publisher", null);
	}
	
	
	public Publisher readPublisherById(Integer publisherId) throws ClassNotFoundException, SQLException {
		
		List<Publisher> Publishers = read("select * from tbl_publisher where publisherId = ?", new Object[]{publisherId});
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
			a.setId(rs.getInt("publisherId"));
			a.setName(rs.getString("publisherName"));
			a.setAddress(rs.getString("publisherAddress"));
			a.setPhone(rs.getString("publisherPhone"));
			a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (select bookId from tbl_publisher where publisherId = ?)" , new Object[]{a.getId()}));
			Publishers.add(a);
		}
		return Publishers;
	}

	@Override
	public List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Publisher> Publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher a = new Publisher();
			a.setId(rs.getInt("publisherId"));
			a.setName(rs.getString("publisherName"));
			a.setAddress(rs.getString("publisherAddress"));
			a.setPhone(rs.getString("publisherPhone"));
			Publishers.add(a);
		}
		return Publishers;
	}

}
