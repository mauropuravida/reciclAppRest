package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.AReciclar;
import com.example.model.Producto;


@Repository

public interface AReciclarRepository extends JpaRepository<AReciclar, Long>{

	void deleteByIdUserAndIdProd(long iduser, long idprod);
	
	
}
