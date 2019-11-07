package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.AReciclar;
import com.example.model.Producto;


@Repository

public interface AReciclarRepository extends JpaRepository<AReciclar, Long>{

	void deleteByIdUserAndIdProd(long iduser, long idprod);
	
	@Query("INSERT INTO historico SELECT * FROM a_reciclar where id_user = :user ")
	@Modifying
	void confirmarReciclados(@Param("user") String user);
	
	
}
