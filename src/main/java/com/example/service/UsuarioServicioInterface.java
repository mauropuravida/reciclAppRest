package com.example.service;

import com.example.model.Usuario;

public interface UsuarioServicioInterface {
	
	public Usuario save(Usuario usu);

	public Object findById(long id);

}
