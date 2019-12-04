package com.example.service;

import java.util.List;

import com.example.model.RecoleccionesHistorial;

public interface RecoleccionesHistorialServicioInterface {
	public RecoleccionesHistorial save(RecoleccionesHistorial recoleccion);
	public List<RecoleccionesHistorial> findAll();
}
