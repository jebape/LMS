package com.gcit.lms.jdbc;

public class Book {

	private Integer id;
	private String title;
	private Publisher pid;
	
	public Book(Integer id, String title, Publisher pid) {
		this.id = id;
		this.title = title;
		this.pid = pid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Publisher getPid() {
		return pid;
	}

	public void setPid(Publisher pid) {
		this.pid = pid;
	}
	
}
