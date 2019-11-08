package com.example.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Historico;
import com.example.repo.HistoricoRepository;

@Service
public class HistoricoServicio implements HistoricoServicioInterface{
	
	@Autowired
	HistoricoRepository historicoRepo;
	
	@Override
	public List<Historico> recuperarDatos(long iduser, Date inicio, Date fin) {
		return historicoRepo.recuperarDatos(iduser,inicio,fin);
	}

	@Override
	public List<Historico> findByIdUser(long iduser) {
		return historicoRepo.findByIdUser(iduser);
	}

	@Override
	public List<Historico> recuperarDatosSoloFin(long iduser, Date fin) {
		return historicoRepo.recuperarDatosSoloFin(iduser,fin);
	}

	@Override
	public List<Historico> recuperarDatosSoloInicio(long iduser, Date inicio) {
		return historicoRepo.recuperarDatosSoloInicio(iduser,inicio);
	}


}
