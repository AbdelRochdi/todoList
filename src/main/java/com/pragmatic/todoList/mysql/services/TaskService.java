package com.pragmatic.todoList.mysql.services;

import java.util.Date;
import java.util.Optional;

import com.pragmatic.todoList.mysql.entities.TaskEntity;

public interface TaskService {

	TaskEntity addTask(TaskEntity taskEntity);
	void deleteTask(Long id);
	Iterable<TaskEntity> findAllTasks();
	Optional<TaskEntity> findTaskById(Long id);
	TaskEntity updateTaskById(TaskEntity taskRequest, Long id);
	TaskEntity findTaskByIdOptional(Long id);
	boolean checkDueDate(Date dueDate);
}
