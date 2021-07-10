package com.pragmatic.todoList.mysql.repositories;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.pragmatic.todoList.mysql.entities.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

	ConfirmationToken findByToken(String token);

	@Transactional
	@Modifying
	@Query("UPDATE ConfirmationToken c " + "SET c.confirmedAt = ?2 " + "WHERE c.token = ?1")
	int updateConfirmedAt(String token, LocalDateTime confirmedAt);

}
