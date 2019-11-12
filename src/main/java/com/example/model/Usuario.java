package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable{

	  @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
	  @Column(name = "id_user")
	  private long idUser;
	  
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
	  
	  @Column(name = "mail")
	  private String mail;

	public Usuario(long idUser, String username, String nombre, String apellido, String password, String address, String mail) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.address = address;
		this.mail = mail;
	}
	
	public Usuario() {

	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "Usuario [idUser=" + idUser + ", username=" + username + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", password=" + password + ", address=" + address + ", mail=" + mail + "]";
	}
	  
	
	  
}
