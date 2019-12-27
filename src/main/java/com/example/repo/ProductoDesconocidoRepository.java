package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Producto;
import com.example.model.Producto_Desconocido;

@Repository
public interface ProductoDesconocidoRepository extends JpaRepository<Producto_Desconocido, Long>{
	Producto_Desconocido findByBarcode(String barcode);

	void deleteById(long id);
}
