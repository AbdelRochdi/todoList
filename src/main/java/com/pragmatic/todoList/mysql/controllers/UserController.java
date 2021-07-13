package com.pragmatic.todoList.mysql.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
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
import com.pragmatic.todoList.mysql.dto.ResetPasswordResquest;
import com.pragmatic.todoList.mysql.entities.UserEntity;
import com.pragmatic.todoList.mysql.services.MyUserDetailsService;
import com.pragmatic.todoList.mysql.services.TokenService;
import com.pragmatic.todoList.mysql.services.UserServiceImpl;
import com.pragmatic.todoList.mysql.utils.JwtUtil;

import io.jsonwebtoken.impl.DefaultClaims;

@Controller
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private TokenService tokenService;

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

		Optional<UserEntity> userEntity = userService.getUser(authenticationRequest.getEmail().toLowerCase());

		if (userEntity.isPresent()) {
			if (userEntity.get().isActive() == false) {
				throw new Exception("This account is not yet activated");
			}
		}

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

	@GetMapping("/confirm")
	public ResponseEntity<String> confirmEmail(@RequestParam("token") String token) {
		String confirm = tokenService.confirmToken(token);
		
		return new ResponseEntity<String>(confirm, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/reset/{userId}")
	public ResponseEntity<String> resetPasswordEmailNotification(@PathVariable Long userId) throws MessagingException {

		String reset = userService.resetPasswordEmail(userId);

		return new ResponseEntity<String>(reset, HttpStatus.OK);

	}
	
	@PostMapping("/reset")
	public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordResquest resetPasswordResquest) throws Exception {
		String confirm = tokenService.confirmPasswordToken(token, resetPasswordResquest);
		
		return new ResponseEntity<String>(confirm, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = jwtUtil.getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
	

	

}
