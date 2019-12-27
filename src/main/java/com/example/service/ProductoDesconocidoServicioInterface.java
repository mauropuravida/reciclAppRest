package com.example.service;


import com.example.model.Producto_Desconocido;

public interface ProductoDesconocidoServicioInterface {
	public Producto_Desconocido save(Producto_Desconocido prod);

	public Producto_Desconocido findByBarcode(String barcode);

	public void delete(long id);

}
