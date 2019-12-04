package com.example.model;

import java.util.ArrayList;

public class UserRegister {
	
	private String user;
	private String pass;
	
	
	
	
	public String getPass() {
		return pass;
	}



	public void setPass(String pass) {
		this.pass = pass;
	}



	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
	}
	
	public ArrayList<String> getAll(){
		ArrayList<String> dats = new ArrayList<String>();
		dats.add("user");dats.add(user);dats.add("pass");dats.add(pass);
		return dats;
	}

}
