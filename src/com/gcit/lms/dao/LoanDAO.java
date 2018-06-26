package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.Loan;
/**
 * @author Jesús Peral
 *
 */
public class LoanDAO extends BaseDAO<Loan> {
	
	public LoanDAO(Connection con) {
		super(con);
	}

	public List<Loan> readAllLoans() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loan", null);
	}
	
	
	public void updateDueDate(Book book, Branch branch, Borrower borrower, LocalDate date) throws ClassNotFoundException, SQLException {
		save("update tbl_book_loans set dueDate = ? where bookId = ? and branchId = ? and cardNo = ?", new Object[] {date.toString(), book.getId(), branch.getId(), borrower.getId()});

	}
	
	public void checkInBook(Book selectedBook, Branch branch, Integer cardNo) throws ClassNotFoundException, SQLException {
		save("update tbl_book_loans set dateIn = ? where bookId = ? and branchId = ? and cardNo = ?", new Object[] {LocalDate.now(), selectedBook.getId(), branch.getId(), cardNo});

		//save("delete from tbl_book_loans where bookId = ? and cardNo = ?", new Object[] { selectedBook.getId(), cardNo });
	}

	public void checkOutBook(Book selectedBook, Branch branchId, Integer cardNo)
			throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) values (?,?,?,?,?,?)",
				new Object[] { selectedBook.getId(), branchId.getId(), cardNo, LocalDate.now(),
						LocalDate.now().plusDays(7), null });
	}

	
	@Override
	public List<Loan> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Loan> loans = new ArrayList<>();
		BranchDAO brdao = new BranchDAO(conn);
		BorrowerDAO bodao = new BorrowerDAO(conn);
		BookDAO bdao = new BookDAO(conn);
		
		while (rs.next()) {
			Loan lo = new Loan();
			lo.setDateOut(rs.getString("dateOut"));
			lo.setDueDate(rs.getString("dueDate"));
			lo.setDateIn(rs.getString("dateIn"));
			lo.setBook(bdao.readBookById(rs.getInt("bookId")));
			lo.setBranch(brdao.readBranchById(rs.getInt("branchId")));
			lo.setBorrower(bodao.readBorrowerByID(rs.getInt("cardNo")));

			loans.add(lo);
		}
		return loans;
	}

	@Override
	public List<Loan> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Loan> loans = new ArrayList<>();
		while (rs.next()) {
			Loan lo = new Loan();
			lo.setDateOut(rs.getString("dateOut"));
			lo.setDueDate(rs.getString("dueDate"));
			lo.setDateIn(rs.getString("dateIn"));
			loans.add(lo);
		}
		return loans;
	}

	public List<Loan> getExistingLoansFromBranch(Integer branchId) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans where bookId in (select bookId from tbl_book_copies where branchId = ?)", new Object[]{branchId});
	}
	public List<Loan> getLoansBy(Integer cardNo) throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loans where cardNo = ? and dateIn is null", new Object[]{cardNo});
	}

	
}
