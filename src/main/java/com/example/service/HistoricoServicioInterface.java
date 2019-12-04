package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.model.Historico;

public interface HistoricoServicioInterface {

	List<Historico> findByIdUser(long iduser);

	List<Historico> recuperarDatosConFechas(long iduser, Date inicio, Date fin, List<Integer> materiales);

}
