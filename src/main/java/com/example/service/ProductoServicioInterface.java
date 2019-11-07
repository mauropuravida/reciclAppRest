package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.Producto;

public interface ProductoServicioInterface {

	public Producto findById(long id);
	public List<Producto> findAll();
	public Producto findByBarcode(String barcode);
	public List<Producto> saveAll(List<Producto> asList);
	
	public Producto save(Producto prod);
	public void delete(Producto prod);
}
