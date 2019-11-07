package com.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "historico")
@IdClass(TwoKeys.class)
public class Historico {
	  @Id
	  @Column(name = "id_user")
	  private long idUser;
	  @Id
	  @Column(name = "id_prod")
	  private long idProd;
	  
	  @Column(name = "cantidad")
	  private int cantidad;
	  
	  @Column(name = "fecha")
	  private Date fecha;

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public long getIdProd() {
		return idProd;
	}

	public void setIdProd(long idProd) {
		this.idProd = idProd;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Historico [idUser=" + idUser + ", idProd=" + idProd + ", cantidad=" + cantidad + ", fecha=" + fecha
				+ "]";
	}
	  
	
}
