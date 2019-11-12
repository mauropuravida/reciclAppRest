package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Categoria;
import com.example.model.ChangePass;

@Repository
public interface ChangePassRepository extends JpaRepository<ChangePass, Long>{
	
	ChangePass save(ChangePass cp);

	ChangePass findByUser(long id);

}
