package com.example.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.model.Historico;

@Repository
@Transactional
public interface HistoricoRepository extends JpaRepository<Historico, Long>{
    @Query(value = "SELECT * FROM historico WHERE id_user = :iduser and fecha_reciclado >= :inicio and fecha <= :fin and id_prod IN :materiales" , nativeQuery=true)
	List<Historico> recuperarDatos(@Param("iduser")long iduser, @Param("inicio")Date inicio,@Param("fin") Date fin, List<Integer> materiales);

	List<Historico> findByIdUser(long iduser);
	
}
