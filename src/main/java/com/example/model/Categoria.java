package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable{
	  @Id
	  @Column(name = "id_prod")
	  private long idProd;
	  
	  @Column(name = "categoria")
	  private String categoria; 
	  
	  @Column(name = "volumen")
	  private String volumen;

	public long getId_prod() {
		return idProd;
	}

	public void setId_prod(long id_prod) {
		this.idProd = id_prod;
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
		return "Categoria [id_prod=" + idProd + ", categoria=" + categoria + ", volumen=" + volumen + "]";
	}

}
