package com.example.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.AReciclar;
import com.example.model.Historico;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long>{
    @Query(value = "SELECT id_prod FROM Historico WHERE id_user = :iduser and fecha >= :inicio and fecha <= :fin", nativeQuery=true)
	List<Historico> recuperarDatos(@Param("iduser")long iduser, @Param("inicio")Date inicio,@Param("fin") Date fin);

	List<Historico> findByIdUser(long iduser);

    @Query(value = "SELECT id_prod FROM Historico WHERE id_user = :iduser and fecha <= :fin", nativeQuery=true)	
	List<Historico> recuperarDatosSoloFin(@Param("iduser")long iduser, @Param("fin")Date fin);

    @Query(value = "SELECT id_prod FROM Historico WHERE id_user = :iduser and fecha <= :fin", nativeQuery=true)	
	List<Historico> recuperarDatosSoloInicio(@Param("iduser")long iduser, @Param("fin")Date inicio);
	
}
