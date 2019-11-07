package com.example.service;

import com.example.model.AReciclar;

public interface AReciclarServicioInterface {
	
	public AReciclar save(AReciclar prod);

	public void deleteByIdUserAndIdProd(long iduser, long idprod);
	
	void confirmarReciclados(String user);

}
