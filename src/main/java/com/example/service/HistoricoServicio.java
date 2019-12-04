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
	public List<Historico> findByIdUser(long iduser) {
		return historicoRepo.findByIdUser(iduser);
	}
	
	@Override
	public List<Historico> recuperarDatosConFechas(long iduser, Date inicio, Date fin , List<Integer> materiales) {
		return historicoRepo.recuperarDatos(iduser,inicio,fin, materiales);
	}
}
