package com.pragmatic.todoList.mysql.services;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pragmatic.todoList.mysql.dto.ResetPasswordResquest;
import com.pragmatic.todoList.mysql.entities.ConfirmationToken;
import com.pragmatic.todoList.mysql.entities.UserEntity;
import com.pragmatic.todoList.mysql.repositories.ConfirmationTokenRepository;
import com.pragmatic.todoList.mysql.repositories.UserRepository;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

		if (confirmationToken == null) {
			throw new IllegalStateException("token not found");
		}
		
		if (!confirmationToken.getType().equals("email")) {
			throw new IllegalStateException("invalid token");
		}

		if (confirmationToken.getConfirmedAt() != null) {
			throw new IllegalStateException("email already confirmed");
		}

		LocalDateTime expiredAt = confirmationToken.getExpiresAt();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}

		setConfirmedAt(token);

		UserEntity tokenUser = confirmationToken.getUserEntity();

		tokenUser.setActive(true);

		userRepository.save(tokenUser);

		return "confirmed";
	}
	
	@Override
	public String confirmPasswordToken(String token, ResetPasswordResquest resetPasswordResquest) throws Exception {
		ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);

		if (confirmationToken == null) {
			throw new IllegalStateException("token not found");
		}
		
		if (!confirmationToken.getType().equals("password")) {
			throw new IllegalStateException("invalid token");
		}

		if (confirmationToken.getConfirmedAt() != null) {
			throw new IllegalStateException("you already reset your password");
		}

		LocalDateTime expiredAt = confirmationToken.getExpiresAt();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}
		
		if(!resetPasswordResquest.getPassword().equals(resetPasswordResquest.getConfirmPassword())) {
			throw new Exception("Your passwords don't match");
		}

		setConfirmedAt(token);

		UserEntity tokenUser = confirmationToken.getUserEntity();

		tokenUser.setEncryptedPassword(bCryptPasswordEncoder.encode(resetPasswordResquest.getPassword()));

		userRepository.save(tokenUser);

		return "Your password has been reset successfully";
	}

	@Override
	public void createToken(ConfirmationToken confirmationToken) {

		confirmationTokenRepository.save(confirmationToken);
	}

	@Override
	public int setConfirmedAt(String token) {
		return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
	}
}
