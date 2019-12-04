package com.example.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Filtro {
	
	private Date fechaInicio;
	private Date fechaFin;
	private List<String> materiales;
	
	public Filtro() {
		materiales = new ArrayList<String>();
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public List<String> getMateriales() {
		return materiales;
	}
	public void setMateriales(List<String> materiales) {
		this.materiales = materiales;
	}
}
