package com.example.service;

import java.util.List;

import com.example.model.Producto;
import com.example.model.Usuario;

public interface UsuarioServicioInterface {
	
	public Usuario save(Usuario usu);

	public Usuario findByIdUser(long id);

	public List<Usuario> findAll();

	public Usuario findByIdUserAndPassword(long idUser, String password);

}
