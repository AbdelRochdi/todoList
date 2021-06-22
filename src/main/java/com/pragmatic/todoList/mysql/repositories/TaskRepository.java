package com.pragmatic.todoList.mysql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragmatic.todoList.mysql.entities.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {


	
}
