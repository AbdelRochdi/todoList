package com.pragmatic.todoList.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragmatic.todoList.entities.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
}
