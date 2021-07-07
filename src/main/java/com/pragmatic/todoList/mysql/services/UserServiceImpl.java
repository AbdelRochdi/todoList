package com.pragmatic.todoList.mysql.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pragmatic.todoList.mysql.entities.UserEntity;
import com.pragmatic.todoList.mysql.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	public UserEntity createUserEntity(UserEntity userEntity) throws Exception {

		Optional<UserEntity> checkUser = userRepository.findByEmail(userEntity.getEmail().toLowerCase());

		if (checkUser.isPresent()) {
			throw new Exception("User already exists");
		} else {

			userEntity.setEmail(userEntity.getEmail().toLowerCase());

			userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userEntity.getEncryptedPassword()));

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
	public UserEntity getUser(String email) {

		UserEntity userEntity = userRepository.findByEmail(email).get();

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return userEntity;
	}

}
