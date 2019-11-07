package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.AReciclar;
import com.example.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<AReciclar, Long> {

	Usuario save(Usuario usu);

}
