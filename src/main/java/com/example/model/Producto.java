package com.example.model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "producto")
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
  private int categoria; 

 
  public Producto(long id_prod, String codigoBarras, String descripcion, int categoria) {
	this.id_prod=id_prod;
    this.barcode=codigoBarras;
	this.descripcion=descripcion;
	this.categoria=categoria;
  }
  
  public Producto() {}
  
  public long getId_prod() {
	  return id_prod;
	}
	
	public void setId_prod(long id_prod) {
		this.id_prod = id_prod;
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
	
	public int getCategoria() {
		return categoria;
	}
	
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	
	@Override
	public String toString() {
		return "Producto [id_prod=" + id_prod + ", barcode=" + barcode + ", descripcion=" + descripcion + ", categoria=" + categoria + "]";
	}
}