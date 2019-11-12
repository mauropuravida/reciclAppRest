package com.example.returns;

public class InfoProducto {
	 
	private long id_prod;
	 
	private String barcode;
	 
	private String descripcion;
	  
	private String categoria;
	
	private long cantidad;
	  
	public InfoProducto(long id_prod, String codigoBarras, String descripcion, String categoria, long cantidad) {
		this.id_prod=id_prod;
	    this.barcode=codigoBarras;
		this.descripcion=descripcion;
		this.categoria=categoria;
		this.cantidad=cantidad;
	}
	  
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
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public long getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public String toString() {
		return "Producto [id_prod=" + id_prod + ", barcode=" + barcode + ", descripcion=" + descripcion + ", categoria="
				+ categoria + cantidad + "]";
	}
	
}
