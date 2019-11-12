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
	Usuario findByIdUser(long id);
	List<Usuario> findAll();
	Usuario findByIdUserAndPassword(long idUser, String password);
	Usuario findByUsernameAndPassword(String usename, String password);
	
	@Modifying
    @Query(value = "UPDATE usuario SET password = :pass WHERE id_user = :user", nativeQuery=true)
	void setPass(@Param("user")long user, @Param("pass")String pass);
	
	//Estas dos query no funcionan, no me tira ningun tipo de error especifico, solo error, igualmente se implemento en la 
	//base de datos con un trigger y un procedure
	/*@Query(value = "SELECT IF(1,'true', 'false')  FROM usuario WHERE username = :username", nativeQuery=true)	
	Boolean buscarPorUsername(@Param("username")String username);
	
	@Query(value = "SELECT IF(1,'true', 'false') FROM usuario WHERE mail = :mail", nativeQuery=true) 
	Boolean buscarPorMail(@Param("mail")String mail);*/
	Usuario findByUsername(String username);
	Usuario findByMail(String mail);

}