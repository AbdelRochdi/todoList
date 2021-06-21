package com.pragmatic.todoList.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pragmatic.todoList.entities.user.UserEntity;
import com.pragmatic.todoList.services.UserService;

@Controller
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserEntity userEntity) {

		UserEntity savedUserEntity = userService.createUserEntity(userEntity);

		return new ResponseEntity<UserEntity>(savedUserEntity, HttpStatus.CREATED);

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllUsers() {
		Iterable<UserEntity> tasks = userService.findAllUsers();
		return new ResponseEntity<Iterable<UserEntity>>(tasks, HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getTaskById(@PathVariable Long userId) {

		UserEntity user = userService.findUserById(userId);

		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);

	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteTaskById(@PathVariable Long userId) {

		userService.deleteUser(userId);

		return new ResponseEntity<String>("Project with Id " + userId + " was deleted.", HttpStatus.NO_CONTENT);

	}

}
