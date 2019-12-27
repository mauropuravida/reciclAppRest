package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	public Usuario findByIdUser(long id) {
		return usuarioRepo.findById(id);
	}

	@Override
	public List<Usuario> findAll() {
		return usuarioRepo.findAll();
	}

	@Override
	public Usuario findByIdUserAndPassword(long idUser, String password) {
		return usuarioRepo.findByIdAndPassword(idUser,password);
	}

	@Override
	public Usuario findByUsernameAndPassword(String usename, String password) {
		return usuarioRepo.findByUsernameAndPassword(usename, password);
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioRepo.findByUsername(username);
	}
	
	@Override
	public void setPass(String pass, long user) {
		usuarioRepo.setPass(user, pass);
	}

}
