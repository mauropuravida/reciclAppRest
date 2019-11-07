package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.model.Producto;
import com.example.repo.ProductoRepository;

@Service
public class ProductoServicio implements ProductoServicioInterface{
	
	@Autowired
	ProductoRepository productoRepo;
	
	@Override
	public Producto findById(long id){
		return productoRepo.findById(id);
	}
	
	@Override
	public List<Producto> findAll(){
		return productoRepo.findAll();
	}
	
	@Override
	public Producto findByBarcode(String barcode){
		return productoRepo.findByBarcode(barcode);
	}

	@Override
	public List<Producto> saveAll(List<Producto> asList) {
		return productoRepo.saveAll(asList);
	}

	@Override
	public Producto save(Producto prod) {
		return productoRepo.save(prod);
	}

	@Override
	public void delete(Producto prod) {
		productoRepo.delete(prod);
		
	}
	
	

}
