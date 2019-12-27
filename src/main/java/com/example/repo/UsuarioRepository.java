package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Usuario;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario save(Usuario usu);
	Usuario findById(long id);
	List<Usuario> findAll();
	Usuario findByIdAndPassword(long idUser, String password);
	Usuario findByUsernameAndPassword(String usename, String password);
	
	@Modifying
    @Query(value = "UPDATE usuario SET password = :pass WHERE id = :user", nativeQuery=true)
	void setPass(@Param("user")long user, @Param("pass")String pass);
	
	Usuario findByUsername(String username);
}