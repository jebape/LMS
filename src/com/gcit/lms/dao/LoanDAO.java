package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Loan;

public class LoanDAO extends BaseDAO<Loan> {
	
	public LoanDAO(Connection con) {
		super(con);
	}

	public List<Loan> readAllLoans() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book_loan", null);
	}
	
	public List<Loan> readLoansByName(String LoanName) throws ClassNotFoundException, SQLException {
		String searchName = "%"+LoanName+"%";
		return read("select * from tbl_book_loan where LoanName like ?", new Object[]{searchName});
	}
	
	public Loan readLoanByPK(Integer loanId) throws ClassNotFoundException, SQLException {
		
		List<Loan> loans = read("select * from tbl_book_loan where LoanId = ?", new Object[]{loanId});
		if(!loans.isEmpty()){
			return loans.get(0);
		}
		return null;
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
			lo.setBook(bdao.readBookByPK(rs.getInt("bookId")));
			lo.setBranch(brdao.readBranchByPK(rs.getInt("branchId")));
			lo.setBorrower(bodao.readBorrowerByID(rs.getInt("bookId")));

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
	
}
