package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "producto_desconocido")
public class Producto_Desconocido implements Serializable{
	
	  @Id
	  //@GeneratedValue(strategy = GenerationType.AUTO)
	  //VER PORQUE NO SE DEBERIA GENERAR AUTOMATICO, DEBERIA TOMAR LA FOREIGN KEY
	  @Column(name = "id")
	  private long id;
	 
	  @Column(name = "barcode")
	  private String barcode;
	  
	  @Column(name = "descripcion")
	  private String descripcion;
	  
	  @Column(name = "texto_c")
	  private String texto_c;
	  
	  @Column(name = "peso")
	  private float peso;
	  
	  @Column(name = "volumen")
	  private float volumen;
	  
	  @Column(name = "consenso")
	  private int consenso;
	  
	  @Column(name = "categoria")
	  private int categoria;
	  
		
	public Producto_Desconocido() {
		barcode="";
		descripcion="";
		texto_c="";
		peso=0;
		volumen=0;
		consenso=0;
		categoria=0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Producto_Desconocido [id_user=" + id + ", barcode=" + barcode + ", categoria=" + categoria + "]";
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTexto_c() {
		return texto_c;
	}

	public void setTexto_c(String texto_c) {
		this.texto_c = texto_c;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public float getVolumen() {
		return volumen;
	}

	public void setVolumen(float volumen) {
		this.volumen = volumen;
	}

	public int getConsenso() {
		return consenso;
	}

	public void setConsenso(int consenso) {
		this.consenso = consenso;
	}	
}


