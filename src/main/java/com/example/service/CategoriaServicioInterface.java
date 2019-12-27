package com.example.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.model.Categoria;

public interface CategoriaServicioInterface {
	public Categoria findByCategoria(String categoria);

	public List<Categoria> findAll();

	public String findByIdProd(long categoria);

}
