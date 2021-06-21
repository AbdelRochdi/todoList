package com.pragmatic.todoList.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pragmatic.todoList.entities.user.UserEntity;
import com.pragmatic.todoList.repositories.user.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserEntity createUserEntity(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}


	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	
	public Iterable<UserEntity> findAllUsers(){
		return userRepository.findAll();
	}
	
	public UserEntity findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	
}
