package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.model.Categoria;
import com.example.repo.CategoriaRepository;

@Service
public class CategoriaServicio implements CategoriaServicioInterface{
	@Autowired
	CategoriaRepository categoriaRepo;
	
	@Override
	public Categoria findByCategoria(String categoria){
		return categoriaRepo.findByCategoria(categoria);
	}

	@Override
	public List<Categoria> findAll() {
		return categoriaRepo.findAll();
	}

	@Override
	public String findByIdProd(long categoria) {
		return categoriaRepo.findByIdProd(categoria).getCategoria();
	}
}
