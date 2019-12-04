package com.example.service;

import com.example.model.ChangePass;

public interface ChangePassServiceInterface {
	public ChangePass save(ChangePass cp);
	public ChangePass findByToken(long id);
	public void delete(long user);
}
