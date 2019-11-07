package com.example.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "productos")
public class Producto  implements Serializable {
 
  @Id
  //@GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private long id_prod;
 
  @Column(name = "barcode")
  private String barcode;
 
  @Column(name = "descripcion")
  private String descripcion;
  
  @Column(name = "categoria")
  private String categoria; 

 
  public Producto(long id_prod, String codigoBarras, String descripcion, String categoria) {
	this.id_prod=id_prod;
    this.barcode=codigoBarras;
	this.descripcion=descripcion;
	this.categoria=categoria;
  }
  
  public long getId_prod() {
	return id_prod;
}

public void setId_prod(long id_prod) {
	this.id_prod = id_prod;
}

protected Producto(){
  }
  	public String getCodigoBarras() {
		return barcode;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.barcode = codigoBarras;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Producto [id_prod=" + id_prod + ", barcode=" + barcode + ", descripcion=" + descripcion + ", categoria="
				+ categoria + "]";
	}
	
	

}