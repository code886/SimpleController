package com.yuanhao.model;

/**
 * @author Administrator
 *
 */
public class UserBean {
	String username;
	String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserBean(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
