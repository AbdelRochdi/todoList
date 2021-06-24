package com.pragmatic.todoList.mysql.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.pragmatic.todoList.mysql.entities.UserEntity;

public interface UserService extends UserDetailsService {

	UserEntity createUserEntity(UserEntity userEntity) throws Exception;

	UserEntity updateUserEntity(Long id, UserEntity userEntity);

	UserEntity getUser(String email);

	Iterable<UserEntity> findAllUsers(int page, int limit);

	UserEntity findUserById(Long id);

	void deleteUser(Long id);

}
