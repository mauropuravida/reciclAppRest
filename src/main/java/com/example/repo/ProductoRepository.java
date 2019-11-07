package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Producto;
 
@Repository //antes esto no estaba, lo agregue el 7/10
public interface ProductoRepository extends JpaRepository<Producto, Long>{ //antes extendia de CrudRepository
	Producto findByBarcode(String barcode);
	Producto findById(long id);
}