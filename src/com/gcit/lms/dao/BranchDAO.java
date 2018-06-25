package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Branch;

public class BranchDAO extends BaseDAO<Branch>{
	
	public BranchDAO(Connection conn) {
		super(conn);
	}
	
	public void addBranch(Branch branch) throws ClassNotFoundException, SQLException{
		save("insert into tbl_library_branch (branchName, branchAddress) values (?,?)",
				new Object[] {branch.getName(),branch.getAddress()});
	}
	
	public void updateBranch(Branch branch) throws ClassNotFoundException, SQLException{
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[]{branch.getName(), branch.getAddress(), branch.getId()});
	}
	
	public void deleteBranch(Branch branch) throws ClassNotFoundException, SQLException{
		save("delete from tbl_library_branch where branchId = ?", new Object[] {branch.getId()});
	}

	
	public List<Branch> readAllBranches() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_library_branch", null);
	}
	
	public List<Branch> readBranchesByName(String BranchName) throws ClassNotFoundException, SQLException {
		String searchName = "%"+BranchName+"%";
		return read("select * from tbl_Branch where BranchName like ?", new Object[]{searchName});
	}
	
	public List<Book> getExistingBooksFromBranch(Integer  branchId) throws ClassNotFoundException, SQLException{
		BookDAO bdao = new BookDAO(conn);
		return bdao.read("select * from tbl_book where bookId in (select bookId from tbl_book_copies where branchId = ? and noOfCopies > 0)", new Object[]{branchId});
	}
	
	public List<Book> getAllBooksFromBranch(Integer  branchId) throws ClassNotFoundException, SQLException{
		BookDAO bdao = new BookDAO(conn);
		return bdao.read("select * from tbl_book where bookId in (select bookId from tbl_book_copies where branchId = ?)", new Object[]{branchId});
	}
	
	public Branch readBranchByPK(Integer BranchId) throws ClassNotFoundException, SQLException {
//		select distinct b.bookId, b.title, b.pubId, bc.noOfCopies from tbl_book b inner join tbl_book_copies bc on bc.bookId = b.bookId
//		where b.bookId like ? and b.title like ? and b.pubId like ? and b.bookId in 
//		(select bookId from tbl_book where bookId in (select bookId from tbl_book_copies where branchId = ?));
		List<Branch> Branchs = read("select * from tbl_library_branch where BranchId = ?", new Object[]{BranchId});
		if(!Branchs.isEmpty()){
			return Branchs.get(0);
		}
		return null;
	}
	
	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Branch> Branchs = new ArrayList<>();
		LoanDAO ldao = new LoanDAO(conn);
		CopyDAO cdao = new CopyDAO(conn);
		while (rs.next()) {
			Branch b = new Branch();
			b.setId(rs.getInt("branchId"));
			b.setName(rs.getString("branchName"));
			b.setAddress(rs.getString("branchAddress"));
			b.setLoans(ldao.readFirstLevel("select * from tbl_book_loans where bookId IN (select bookId from tbl_library_branch where BranchId = ?)" , new Object[]{b.getId()}));
			b.setCopies(cdao.readFirstLevel("select * from tbl_book_copies where bookId IN (select bookId from tbl_library_branch where BranchId = ?)" , new Object[]{b.getId()}));
			Branchs.add(b);
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
