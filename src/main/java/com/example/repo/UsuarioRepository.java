package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario save(Usuario usu);
	Usuario findByIdUser(long id);
	List<Usuario> findAll();
	Usuario findByIdUserAndPassword(long idUser, String password);
	Usuario findByUsernameAndPassword(String usename, String password);

}