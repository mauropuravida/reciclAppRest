package com.example.model;

public class DataChange {
	private String token;
	private String pass;
	private String user;

	public DataChange() {
		
	}
	
	public void setToken(String token) {
		this.token=token;
	}
	
	public void setPass(String pass) {
		this.pass=pass;
	}
	
	public String getToken() {
		return token;
	}

	public String getPass() {
		return pass;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
