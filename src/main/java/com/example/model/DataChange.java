package com.example.model;

import java.util.ArrayList;

public class DataChange {
	private String token;
	private String pass;
	private String user;

	public DataChange() {}
	
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
	
	public ArrayList<String> getAll(){
		ArrayList<String> dats = new ArrayList<String>();
		dats.add("token");dats.add(token);dats.add("user");dats.add(user);
		dats.add("pass");dats.add(pass);
		return dats;
	}

}
