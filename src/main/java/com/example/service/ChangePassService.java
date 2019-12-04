package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ChangePass;
import com.example.repo.ChangePassRepository;

@Service
public class ChangePassService implements ChangePassServiceInterface {
	@Autowired
	ChangePassRepository changePassRepo;
	

	@Override
	public ChangePass save(ChangePass cp) {
		return changePassRepo.save(cp);
	}
	
	@Override
	public ChangePass findByToken(long id) {
		return changePassRepo.findByUser(id);
	}

	@Override
	public void delete(long user) {
		changePassRepo.deleteAllByUser(user);
	}

}
