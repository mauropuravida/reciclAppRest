package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable{
	  @Id
	  @Column(name = "id_prod")
	  private long id_prod;
	  
	  @Column(name = "categoria")
	  private String categoria; 
	  
	  @Column(name = "volumen")
	  private String volumen;

	public long getId_prod() {
		return id_prod;
	}

	public void setId_prod(long id_prod) {
		this.id_prod = id_prod;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getVolumen() {
		return volumen;
	}

	public void setVolumen(String volumen) {
		this.volumen = volumen;
	}

	@Override
	public String toString() {
		return "Categoria [id_prod=" + id_prod + ", categoria=" + categoria + ", volumen=" + volumen + "]";
	}

}
