package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Notificacion;
import com.example.repo.NotificacionesRepository;

@Service
public class NotificacionesServicio implements NotificacionesServicioInterface{
	
	@Autowired
	NotificacionesRepository notificacionesRepo;

	@Override
	public List<Notificacion> findAll() {
		return notificacionesRepo.findAll();
	}

}
