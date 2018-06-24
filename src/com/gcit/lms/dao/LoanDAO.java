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

	public void addLoan(Loan Loan) throws ClassNotFoundException, SQLException {
		//save("insert into tbl_Loan (LoanName) values (?)", new Object[] { Loan.getName() });
	}
	
	public void editLoan(Loan Loan) throws ClassNotFoundException, SQLException {
		//save("update tbl_book_loans set LoanName = ? where LoanId = ?",
				//new Object[] { Loan.getName(), Loan.getId() });
	}
	
	public void deleteLoan(Loan Loan) throws ClassNotFoundException, SQLException {
		//save("delete from tbl_Loan where LoanId = ?", new Object[] { Loan.getId() });
	}
	
	public List<Loan> readAllLoans() throws ClassNotFoundException, SQLException {
		//return read("select * from tbl_book_loan", null);
		return null;
	}
	
	public List<Loan> readLoansByName(String LoanName) throws ClassNotFoundException, SQLException {
		//String searchName = "%"+LoanName+"%";
		//return read("select * from tbl_book_loan where LoanName like ?", new Object[]{searchName});
		return null;
	}
	
	public Loan readLoanByPK(Integer LoanId) throws ClassNotFoundException, SQLException {
		/*
		List<Loan> Loans = read("select * from tbl_book_loan where LoanId = ?", new Object[]{LoanId});
		if(!Loans.isEmpty()){
			return Loans.get(0);
		}*/
		return null;
	}
	
	@Override
	public List<Loan> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Loan> Loans = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Loan a = new Loan();
			a.setDateOut(rs.getString("dateOut"));
			a.setDueDate(rs.getString("dueDate"));
			a.setDateIn(rs.getString("dateIn"));
			
			//a.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId IN (select bookId from tbl_book_Loans where LoanId = ?)" , new Object[]{a.getId()}));
			Loans.add(a);
		}
		return Loans;
	}

	@Override
	public List<Loan> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Loan> Loans = new ArrayList<>();
		while (rs.next()) {
			Loan a = new Loan();
			a.setDateOut(rs.getString("dateOut"));
			a.setDueDate(rs.getString("dueDate"));
			a.setDateIn(rs.getString("dateIn"));
			Loans.add(a);
		}
		return Loans;
	}
	
}
