package com.example.returns;

public class InfoProducto {
	 
	private long id_prod;
	 
	private String barcode;
	 
	private String descripcion;
	  
	private String categoria;
	
	private long cantidad;
	
	private boolean consenso;
	
	private float volumen;
	
	private float peso;
	
	public InfoProducto() {
		consenso = false;
	}
	  
	public InfoProducto(long id_prod, String barcode, String descripcion, String categoria, long cantidad) {
		this.id_prod=id_prod;
	    this.barcode=barcode;
		this.descripcion=descripcion;
		this.categoria=categoria;
		this.cantidad=cantidad;
	}
	
	public void setConsenso(boolean c) {
		consenso = c;
	}
	
	public boolean getConsenso() {
		return consenso;
	}
	  
	public long getId_prod() {
		return id_prod;
	}

	public void setId_prod(long id_prod) {
		this.id_prod = id_prod;
	}
	
	public String getBarcode() {
		return barcode;
	}
	
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
				+ categoria + ", cantidad=" + cantidad + ", volumen=" + volumen + ", peso=" + peso + ", consenso=" + consenso + "]";
	}

	public float getVolumen() {
		return volumen;
	}

	public void setVolumen(float volumen) {
		this.volumen = volumen;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}
	
}
