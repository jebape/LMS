/**
 * 
 */
package com.gcit.lms.entity;

import java.util.List;

/**
 * @author Jesús Peral
 *
 */
public class Author {

	private Integer id;
	private String name;
	private List<Book> books;
	
	/**
	 * @return the authors
	 */
	public List<Book> getBooks() {
		return this.books;
	}

	/**
	 * @param authors the authors to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
