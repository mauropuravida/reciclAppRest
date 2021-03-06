package com.example.service;

import java.util.List;

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
		//si existe el producto, sumo lo que ya tengo
		AReciclar ar = findByIdUserAndIdProd(areciclar.getId_user(), areciclar.getId_prod());
		if ( ar != null){
			areciclar.setCantidad(areciclar.getCantidad()+ar.getCantidad());
		}
		return AReciclarRepo.save(areciclar);
	}

	@Override
	public void deleteByIdUserAndIdProd(long iduser, long idprod) {
		AReciclarRepo.deleteByIdUserAndIdProd(iduser,idprod);
		
	}

	@Override
	public void confirmar(AReciclar reciclable) {
		AReciclarRepo.confirmar(reciclable.getId_user(),reciclable.getId_prod());
	}

	@Override
	public AReciclar findByIdUserAndIdProd(long iduser, long idprod) {
		return AReciclarRepo.findByIdUserAndIdProd(iduser,idprod);
	}

	@Override
	public List<AReciclar> findByIdUser(long iduser) {
		return AReciclarRepo.findByIdUser(iduser);
	}
	
	/*@Override
	public void confirmarReciclados(String user) {
		AReciclarRepo.confirmarReciclados(user);
		
	}*/
}