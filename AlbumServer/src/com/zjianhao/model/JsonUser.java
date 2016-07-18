package com.zjianhao.model;

import java.io.Serializable;

public class JsonUser implements Serializable{
	private int id;
	private String username;
	private String email;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public JsonUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JsonUser(Integer userId, String username, String email) {
		super();
		this.id = userId;
		this.username = username;
		this.email = email;
	}
	

}
