package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.model.ChangePass;

@Repository
@Transactional
public interface ChangePassRepository extends JpaRepository<ChangePass, Long>{
	
	ChangePass save(ChangePass cp);

	ChangePass findByUser(long id);
	
	ChangePass findByToken(String token);
	
	@Modifying
	@Query(value = "DELETE FROM change_pass WHERE user = :user", nativeQuery=true)
	void deleteAllByUser(@Param("user")long user);

}
