package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Producto;
import com.example.model.Producto_Desconocido;
import com.example.repo.ProductoDesconocidoRepository;

@Service
public class ProductoDesconocidoServicio implements ProductoDesconocidoServicioInterface{
	@Autowired
	ProductoDesconocidoRepository prodDescRepo;
	
	@Override
	public Producto_Desconocido save(Producto_Desconocido prodDesc) {
		return prodDescRepo.save(prodDesc);
	}

	@Override
	public Producto_Desconocido findByBarcode(String barcode) {
		return prodDescRepo.findByBarcode(barcode);
	}

	@Override
	public void delete(long id) {
		prodDescRepo.deleteById(id);	
	}
}
