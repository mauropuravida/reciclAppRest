package com.example.model;

import java.util.ArrayList;

public class CreateUsuario {

	  private String response;
	  
	  private String username;

	  private String nombre;

	  private String apellido;

	  private String password;

	  private String address;
	  
	public CreateUsuario() {

	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CreateUsuario [response=" + response + ", username=" + username + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", password=" + password + ", address=" + address + "]";
	}
	  
	public ArrayList<String> getAll(){
		ArrayList<String> dats = new ArrayList<String>();
		dats.add("username");dats.add(username);dats.add("nombre");dats.add(nombre);
		dats.add("apellido");dats.add(apellido);dats.add("password");dats.add(password);
		dats.add("address");dats.add(address);
		return dats;
	}
}
