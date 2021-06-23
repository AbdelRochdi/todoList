package com.pragmatic.todoList.mysql.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.pragmatic.todoList.mysql.entities.UserEntity;
import com.pragmatic.todoList.mysql.services.UserServiceImpl;

@Controller
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@PostMapping("")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserEntity userEntity) throws Exception {

		UserEntity savedUserEntity = userService.createUserEntity(userEntity);

		return new ResponseEntity<UserEntity>(savedUserEntity, HttpStatus.CREATED);

	}

	@GetMapping("/all")
	public ResponseEntity<List<UserEntity>> getAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "15") int limit) {

		List<UserEntity> users = userService.findAllUsers(page, limit);

		return new ResponseEntity<>(users, HttpStatus.OK);

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
