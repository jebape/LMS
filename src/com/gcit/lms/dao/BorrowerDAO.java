package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Borrower;

/**
 * @author Jesús Peral
 *
 */
public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	public void addBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("insert into tbl_borrower (name, address, phone) values (?,?,?)",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}

	public void updateBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getId() });
	}

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("delete from tbl_borrower where cardNo = ?", new Object[] { borrower.getId() });
	}

	public List<Borrower> readAllBorrowers() throws ClassNotFoundException, SQLException {

		return read("select * from tbl_borrower", null);
	}

	public Borrower readBorrowerByID(Integer borrowerID) throws ClassNotFoundException, SQLException {
		List<Borrower> borrowers = read("select * from tbl_borrower where cardNo = ?", new Object[] { borrowerID });
		if (borrowers != null && !borrowers.isEmpty()) {
			return borrowers.get(0);
		}
		return null;
	}

	
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		LoanDAO ldao = new LoanDAO(conn);
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower b = new Borrower();
			b.setId(rs.getInt("cardNo"));
			String name = rs.getString("name");
			String address = rs.getString("address");
			String phone = rs.getString("phone");

			b.setName(name);

			b.setAddress(address);

			b.setPhone(phone);

			b.setLoans(ldao.readFirstLevel(
					"select * from tbl_book_loans where bookId IN (select bookId from tbl_library_branch where BranchId = ?)",
					new Object[] { b.getId() }));

			borrowers.add(b);
		}
		return borrowers;
	}

	@Override
	public List<Borrower> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower b = new Borrower();
			b.setId(rs.getInt("cardNo"));
			String name = rs.getString("name");
			String address = rs.getString("address");
			String phone = rs.getString("phone");

			b.setName(name);

			b.setAddress(address);

			b.setPhone(phone);

			borrowers.add(b);
		}
		return borrowers;
	}

}
