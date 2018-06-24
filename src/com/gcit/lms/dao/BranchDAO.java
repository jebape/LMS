package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Branch;

public class BranchDAO extends BaseDAO<Branch>{
	
	public BranchDAO(Connection conn) {
		super(conn);
	}
	
	public void addBranch(Branch Branch) throws ClassNotFoundException, SQLException {
		save("insert into tbl_Branch (BranchName) values (?)", new Object[] { Branch.getName() });
	}
	
	public void editBranch(Branch Branch) throws ClassNotFoundException, SQLException {
		save("update tbl_Branch set BranchName = ? where BranchId = ?",
				new Object[] { Branch.getName(), Branch.getId() });
	}
	
	public void deleteBranch(Branch Branch) throws ClassNotFoundException, SQLException {
		save("delete from tbl_Branch where BranchId = ?", new Object[] { Branch.getId() });
	}
	
	public List<Branch> readAllBranches() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_library_branch", null);
	}
	
	public List<Branch> readBranchesByName(String BranchName) throws ClassNotFoundException, SQLException {
		String searchName = "%"+BranchName+"%";
		return read("select * from tbl_Branch where BranchName like ?", new Object[]{searchName});
	}
	
	public Branch readBranchByPK(Integer BranchId) throws ClassNotFoundException, SQLException {
		
		List<Branch> Branchs = read("select * from tbl_Branch where BranchId = ?", new Object[]{BranchId});
		if(!Branchs.isEmpty()){
			return Branchs.get(0);
		}
		return null;
	}
	
	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Branch> Branchs = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		LoanDAO ldao = new LoanDAO(conn);
		while (rs.next()) {
			Branch a = new Branch();
			a.setId(rs.getInt("BranchId"));
			a.setName(rs.getString("BranchName"));
			a.setAddress(rs.getString("branchAddress"));
			a.setLoans(ldao.readFirstLevel("select * from tbl_book_loans where bookId IN (select bookId from tbl_library_branch where BranchId = ?)" , new Object[]{a.getId()}));
			Branchs.add(a);
		}
		return Branchs;
	}

	@Override
	public List<Branch> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Branch> Branchs = new ArrayList<>();
		while (rs.next()) {
			Branch a = new Branch();
			a.setId(rs.getInt("BranchId"));
			a.setName(rs.getString("BranchName"));
			a.setAddress(rs.getString("branchAddress"));
			Branchs.add(a);
		}
		return Branchs;
	}

}
