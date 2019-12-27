package com.example.service;

import com.example.model.ChangePass;

public interface ChangePassServiceInterface {
	public ChangePass save(ChangePass cp);
	public void delete(long user);
	public ChangePass findByToken(String token);
}
