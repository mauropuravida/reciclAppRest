package com.example.service;

import java.util.List;

import com.example.model.AReciclar;

public interface AReciclarServicioInterface {
	
	public AReciclar save(AReciclar prod );

	public void deleteByIdUserAndIdProd(long iduser, long idprod);

	public void confirmar(AReciclar reciclable);

	public AReciclar findByIdUserAndIdProd(long iduser, long idprod);

	public List<AReciclar> findByIdUser(long iduser);
	
	//void confirmarReciclados(String user);

}
