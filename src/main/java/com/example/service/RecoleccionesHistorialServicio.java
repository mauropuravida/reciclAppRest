package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.RecoleccionesHistorial;
import com.example.repo.RecoleccionesRepository;

@Service
public class RecoleccionesHistorialServicio implements RecoleccionesHistorialServicioInterface{
	@Autowired
	RecoleccionesRepository recolecciones;

	@Override
	public RecoleccionesHistorial save(RecoleccionesHistorial recoleccion) {
		return recolecciones.save(recoleccion);
	}

	@Override
	public List<RecoleccionesHistorial> findAll() {
		return recolecciones.findAll();
	}

}
