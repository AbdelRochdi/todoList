package com.pragmatic.todoList.mysql.services;

import java.util.Optional;

import javax.mail.MessagingException;

import com.pragmatic.todoList.mysql.entities.UserEntity;

public interface UserService {

	UserEntity createUserEntity(UserEntity userEntity) throws Exception;

	UserEntity updateUserEntity(Long id, UserEntity userEntity);

	Optional<UserEntity> getUser(String email);

	Iterable<UserEntity> findAllUsers(int page, int limit);

	UserEntity findUserById(Long id);

	void deleteUser(Long id);
	
	String resetPasswordEmail(Long userId) throws MessagingException;

}
