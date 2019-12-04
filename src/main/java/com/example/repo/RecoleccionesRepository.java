package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.RecoleccionesHistorial;

public interface RecoleccionesRepository extends JpaRepository<RecoleccionesHistorial,Long>{
	RecoleccionesHistorial save(RecoleccionesHistorial recoleccion);
	List<RecoleccionesHistorial> findAll();
}
