package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.AReciclar;
import com.example.model.Usuario;
import com.example.repo.UsuarioRepository;

@Service
public class UsuarioServicio implements UsuarioServicioInterface{
	
	@Autowired
	UsuarioRepository usuarioRepo;

	@Override
	public Usuario save(Usuario usu) {
		return usuarioRepo.save(usu);
	}

	@Override
	public Object findById(long id) {
		return usuarioRepo.findById(id);
	}
}
