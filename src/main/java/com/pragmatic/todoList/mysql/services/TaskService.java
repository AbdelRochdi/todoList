package com.pragmatic.todoList.mysql.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pragmatic.todoList.mysql.entities.TaskEntity;
import com.pragmatic.todoList.mysql.repositories.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public TaskEntity addTask(TaskEntity taskEntity) {
		return taskRepository.save(taskEntity);
	}
	
	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}
	
	public Iterable<TaskEntity> findAllTasks(){
		return taskRepository.findAll();
	}
	
	public TaskEntity findTaskById(Long id) {
		return taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	public TaskEntity updateTaskById(TaskEntity taskRequest, Long id) {
		
		TaskEntity task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task"));
		
		task.setTitle(taskRequest.getTitle());
		task.setDueDate(taskRequest.getDueDate());
		
		return taskRepository.save(task);
		
	}
}
