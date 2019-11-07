package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.AReciclar;
import com.example.model.Producto_Desconocido;
import com.example.repo.AReciclarRepository;

@Service
@Transactional
public class AReciclarServicio implements AReciclarServicioInterface{
	
	@Autowired
	AReciclarRepository AReciclarRepo;
	
	@Override
	public AReciclar save(AReciclar areciclar) {
		return AReciclarRepo.save(areciclar);
	}

	@Override
	public void deleteByIdUserAndIdProd(long iduser, long idprod) {
		AReciclarRepo.deleteByIdUserAndIdProd(iduser,idprod);
		
	}
	
	@Override
	public void confirmarReciclados(String user) {
		AReciclarRepo.confirmarReciclados(user);
		
	}
}
