package com.pragmatic.todoList.mysql.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pragmatic.todoList.mysql.entities.ConfirmationToken;
import com.pragmatic.todoList.mysql.entities.UserEntity;
import com.pragmatic.todoList.mysql.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private MailingService mailingService;

	@Transactional
	public UserEntity createUserEntity(UserEntity userEntity) throws Exception {

		Optional<UserEntity> checkUser = userRepository.findByEmail(userEntity.getEmail().toLowerCase());

		if (checkUser.isPresent()) {
			throw new Exception("User already exists");
		} else {

			userEntity.setEmail(userEntity.getEmail().toLowerCase());

			userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userEntity.getEncryptedPassword()));

			String token = UUID.randomUUID().toString();

			ConfirmationToken confirmationToken = new ConfirmationToken();

			confirmationToken.setToken(token);
			confirmationToken.setCreatedAt(LocalDateTime.now());
			confirmationToken.setExpiresAt(LocalDateTime.now().plusDays(7));
			confirmationToken.setType("email");

			userEntity.addConfirmationToken(confirmationToken);

			String link = "http://localhost:8000/api/users/confirm?token=" + token;

			Map<String, Object> templateModel = new HashMap<String, Object>();

			templateModel.put("link", link);
			templateModel.put("button", "Verify Email Now");
			templateModel.put("text1", "Please verify your email address to");
			templateModel.put("text2", "get access to thousands of exclusive job listings");
			templateModel.put("logo", "logo");

			mailingService.sendMessageUsingThymeleafTemplate(userEntity, "TodoList verification email",
					"C:\\Users\\abdel\\Desktop\\FUNimages\\55.jpg", templateModel);

			return userRepository.save(userEntity);

		}

	}

	public void deleteUser(Long id) {

		UserEntity userEntity = findUserById(id);

		if (userEntity == null)
			throw new UsernameNotFoundException("User with id : " + id + " was not found");

		userRepository.delete(userEntity);
	}

	public List<UserEntity> findAllUsers(int page, int limit) {
		if (page > 0)
			page -= 1;

		Pageable pageable = PageRequest.of(page, limit);

		Page<UserEntity> userPage = userRepository.findAll(pageable);

		List<UserEntity> users = userPage.getContent();

		return users;
	}

	public UserEntity findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	@Override
	public UserEntity updateUserEntity(Long id, UserEntity userEntity) {

		UserEntity updatedUser = findUserById(id);

		if (userEntity == null)
			throw new UsernameNotFoundException("User with id : " + id + " was not found");

		updatedUser.setFirstName(userEntity.getFirstName());
		updatedUser.setLastName(userEntity.getLastName());

		UserEntity userUpdated = userRepository.save(updatedUser);

		return userUpdated;
	}

	@Override
	public Optional<UserEntity> getUser(String email) {

		Optional<UserEntity> userEntity = userRepository.findByEmail(email);

		return userEntity;
	}

	@Override
	public String resetPasswordEmail(Long userId) throws MessagingException {

		Optional<UserEntity> userEntity = userRepository.findById(userId);

		if (userEntity.isPresent()) {
			String token = UUID.randomUUID().toString();

			ConfirmationToken confirmationToken = new ConfirmationToken();

			confirmationToken.setToken(token);
			confirmationToken.setCreatedAt(LocalDateTime.now());
			confirmationToken.setExpiresAt(LocalDateTime.now().plusDays(7));
			confirmationToken.setType("password");

			userEntity.get().addConfirmationToken(confirmationToken);

			String link = "http://localhost:8000/api/users/reset?token=" + token;

			Map<String, Object> templateModel = new HashMap<String, Object>();

			templateModel.put("link", link);
			templateModel.put("button", "Reset Password Now");
			templateModel.put("name", userEntity.get().getFirstName());
			templateModel.put("text1", "Please click here to reset your password");
			templateModel.put("logo", "logo");

			
			mailingService.sendMessageUsingThymeleafTemplate(userEntity.get(), "TodoList Password Reset",
					"C:\\Users\\abdel\\Desktop\\FUNimages\\55.jpg", templateModel);

			userRepository.save(userEntity.get());

			return "Password reset email has been sent";
		} else {
			throw new UsernameNotFoundException("User with id " + userId + " was not found");
		}

	}

}
