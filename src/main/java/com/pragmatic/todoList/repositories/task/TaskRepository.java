package com.pragmatic.todoList.repositories.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pragmatic.todoList.entities.task.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {


	
}
