package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Notificacion;

@Repository
public interface NotificacionesRepository extends JpaRepository<Notificacion, Long>{

	List<Notificacion> findAll();

}
