package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "producto_desconocido")
@IdClass(ThreeKeys.class)
public class Producto_Desconocido implements Serializable{
	
	  @Id
	  //@GeneratedValue(strategy = GenerationType.AUTO)
	  //VER PORQUE NO SE DEBERIA GENERAR AUTOMATICO, DEBERIA TOMAR LA FOREIGN KEY
	  @Column(name = "id_user")
	  private long id_user;
	 
	  @Id
	  @Column(name = "barcode")
	  private String barcode;
	  
	  @Id
	  @Column(name = "categoria")
	  private String categoria;

	public long getId_user() {
		return id_user;
	}

	public void setId_user(long id_user) {
		this.id_user = id_user;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Producto_Desconocido [id_user=" + id_user + ", barcode=" + barcode + ", categoria=" + categoria + "]";
	} 
	
}


