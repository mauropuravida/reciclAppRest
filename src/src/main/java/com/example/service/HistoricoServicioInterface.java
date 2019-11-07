package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.model.Historico;

public interface HistoricoServicioInterface {

	List<Historico> recuperarDatos(long iduser, Date inicio, Date fin);

	List<Historico> findByIdUser(long iduser);

	List<Historico> recuperarDatosSoloFin(long iduser, Date fin);

	List<Historico> recuperarDatosSoloInicio(long iduser, Date inicio);

}
