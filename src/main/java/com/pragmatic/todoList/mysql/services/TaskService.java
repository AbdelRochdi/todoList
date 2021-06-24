package com.pragmatic.todoList.mysql.services;

import java.util.Optional;

import com.pragmatic.todoList.mysql.entities.TaskEntity;

public interface TaskService {

	public TaskEntity addTask(TaskEntity taskEntity);
	public void deleteTask(Long id);
	public Iterable<TaskEntity> findAllTasks();
	public Optional<TaskEntity> findTaskById(Long id);
	public TaskEntity updateTaskById(TaskEntity taskRequest, Long id);
	TaskEntity findTaskByIdOptional(Long id);

}
