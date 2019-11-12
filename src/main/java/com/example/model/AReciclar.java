package com.example.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "A_Reciclar")
@IdClass(TwoKeys.class)
public class AReciclar implements Serializable{
	
	  @Id
	  //@GeneratedValue(strategy = GenerationType.AUTO)
	  //VER PORQUE NO SE DEBERIA GENERAR AUTOMATICO, DEBERIA TOMAR LA FOREIGN KEY
	  @Column(name = "id_user")
	  private long idUser;
	  @Id
	  @Column(name = "id_prod")
	  private long idProd;
	  
	  @Column(name = "cantidad")
	  private int cantidad;
	  
	  @Column(name = "confirmacion")
	  private boolean confirmacion;

	public AReciclar(long id_user, long id_prod, int cantidad, boolean confirmacion) {
		this.idUser = id_user;
		this.idProd = id_prod;
		this.cantidad = cantidad;
		this.confirmacion = confirmacion;
	}
	
	public AReciclar() {
	}

	public long getId_user() {
		return idUser;
	}

	public void setId_user(long id_user) {
		this.idUser = id_user;
	}

	public long getId_prod() {
		return idProd;
	}

	public void setId_prod(long id_prod) {
		this.idProd = id_prod;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	public boolean getConfirmacion() {
		return confirmacion;
	}
	
	public void setConfirmacion(boolean confirmacion) {
		this.confirmacion = confirmacion;
	}
	

	@Override
	public String toString() {
		return "AReciclar [id_user=" + idUser + ", id_prod=" + idProd + ", cantidad=" + cantidad + "]";
	} 
	
	

}
