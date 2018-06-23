/**
 * 
 */
package com.gcit.lms.entity;

import java.util.List;

/**
 * @author Jesús Peral
 *
 */
public class Branch {

	private Integer id;
	private String name;
	private String address;
	private List<Loan> loans;
	private List<Copy> copies;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the loans
	 */
	public List<Loan> getLoans() {
		return loans;
	}
	/**
	 * @param loans the loans to set
	 */
	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}
	/**
	 * @return the copies
	 */
	public List<Copy> getCopies() {
		return copies;
	}
	/**
	 * @param copies the copies to set
	 */
	public void setCopies(List<Copy> copies) {
		this.copies = copies;
	}
}
