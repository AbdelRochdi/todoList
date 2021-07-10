package com.pragmatic.todoList.mysql.services;

import com.pragmatic.todoList.mysql.dto.ResetPasswordResquest;
import com.pragmatic.todoList.mysql.entities.ConfirmationToken;

public interface TokenService {

	String confirmToken(String token);
	
	void createToken(ConfirmationToken confirmationToken);
	
	int setConfirmedAt(String token);

	String confirmPasswordToken(String token, ResetPasswordResquest resetPasswordResquest) throws Exception;
	
}
