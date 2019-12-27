package com.example.model;

import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario{

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long id;
	  
	  @Column(name = "username")
	  private String username;
	  
	  @Column(name = "nombre")
	  private String nombre;
	  
	  @Column(name = "apellido")
	  private String apellido;
	  
	  @Column(name = "password")
	  private String password;
	  
	  @Column(name = "address")
	  private String address;
	  
	public Usuario(long idUser, String username, String nombre, String apellido, String password, String address) {
		this.id = idUser;
		this.username = username;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.address = address;
	}
	
	public Usuario() {

	}

	public long getIdUser() {
		return id;
	}

	public void setIdUser(long idUser) {
		this.id = idUser;
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
		return "Usuario [idUser=" + id + ", username=" + username + ", nombre=" + nombre + ", apellido=" + apellido
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
