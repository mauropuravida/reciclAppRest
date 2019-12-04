package com.example.model;

public class UsuarioResponse {

	  private String username;

	  private String nombre;

	  private String apellido;

	  private String address;

	public UsuarioResponse() {
		username="";
		nombre="";
		apellido="";
		address="";
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
