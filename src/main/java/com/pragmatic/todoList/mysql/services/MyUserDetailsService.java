package com.pragmatic.todoList.mysql.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pragmatic.todoList.mysql.entities.UserEntity;
import com.pragmatic.todoList.mysql.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email.toLowerCase()).get();

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		authorities.add(new SimpleGrantedAuthority(userEntity.getUserRole().getTitle()));

		return new User(userEntity.getEmail().toLowerCase(), userEntity.getEncryptedPassword(), authorities);
	}

}
