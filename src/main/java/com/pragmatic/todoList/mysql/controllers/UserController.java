package com.pragmatic.todoList.mysql.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pragmatic.todoList.mysql.dto.AuthenticationRequest;
import com.pragmatic.todoList.mysql.dto.AuthenticationResponse;
import com.pragmatic.todoList.mysql.entities.UserEntity;
import com.pragmatic.todoList.mysql.services.MyUserDetailsService;
import com.pragmatic.todoList.mysql.services.UserServiceImpl;
import com.pragmatic.todoList.mysql.utils.JwtUtil;

@Controller
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

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

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getEmail().toLowerCase(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userdetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail().toLowerCase());

		final String jwt = jwtUtil.generateToken(userdetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));

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
