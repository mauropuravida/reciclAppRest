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
      @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "id_user")
	  private long idUser;
	  
	  @Column(name = "id_prod")
	  private long username;
	  
	  @Column(name = "nombre")
	  private int nombre;
	  
	  @Column(name = "apellido")
	  private int apellido;
	  
	  @Column(name = "password")
	  private int password;
	  
	  @Column(name = "address")
	  private int address;
	  
	  @Column(name = "mail")
	  private int mail;

	public Usuario(long idUser, long username, int nombre, int apellido, int password, int address, int mail) {
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

	public long getUsername() {
		return username;
	}

	public void setUsername(long username) {
		this.username = username;
	}

	public int getNombre() {
		return nombre;
	}

	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	public int getApellido() {
		return apellido;
	}

	public void setApellido(int apellido) {
		this.apellido = apellido;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public int getMail() {
		return mail;
	}

	public void setMail(int mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "Usuario [idUser=" + idUser + ", username=" + username + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", password=" + password + ", address=" + address + ", mail=" + mail + "]";
	}
	  
	
	  
}
