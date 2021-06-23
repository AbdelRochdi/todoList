package com.pragmatic.todoList.mysql.services;

import com.pragmatic.todoList.mysql.entities.TaskEntity;

public interface TaskService {

	public TaskEntity addTask(TaskEntity taskEntity);

	public void deleteTask(Long id);

	public Iterable<TaskEntity> findAllTasks();

	public TaskEntity findTaskById(Long id);

	public TaskEntity updateTaskById(TaskEntity taskRequest, Long id);

}
