/**
 * 
 */
package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Copy;

/**
 * @author Jesús Peral
 *
 */
public class CopyDAO extends BaseDAO<Copy>{

	public CopyDAO(Connection conn) {
		super(conn);
	}
	
	public void addCopy(Copy copy) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_copies (title) values (?)", new Object[] { copy.getBook() });
	}
	
	public Integer getNumberCopies(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException {
		List<Copy> copies = read("select * from tbl_book_copies where bookId = ? AND branchId = ?", new Object[]{bookId, branchId});
		if(!copies.isEmpty() && copies!=null){
			return copies.get(0).getNoOfCopies();
		}
		return null;	
	}
	
	public void updateNoOfCopies(Integer bookId, Integer branchId, Integer noCopies) throws ClassNotFoundException, SQLException{
			save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",	new Object[] { noCopies, bookId, branchId });
	}

	@Override
	public List<Copy> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Copy> copies = new ArrayList<>();
		BranchDAO brdao = new BranchDAO(conn);
		BookDAO bdao = new BookDAO(conn);
	
		while (rs.next()) {
			Copy c = new Copy();
			c.setNoOfCopies(rs.getInt("noOfCopies"));
			c.setBook(bdao.readBookById(rs.getInt("bookId")));
			c.setBranch(brdao.readBranchByPK(rs.getInt("branchId")));
			copies.add(c);
		}
		return copies;
	}

	@Override
	public List<Copy> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Copy> copies = new ArrayList<>();
		while (rs.next()) {
			Copy c = new Copy();
			c.setNoOfCopies(rs.getInt("noOfCopies"));
			copies.add(c);
		}
		return copies;
	}
}
