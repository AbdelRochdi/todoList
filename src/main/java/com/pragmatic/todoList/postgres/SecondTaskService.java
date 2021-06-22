package com.pragmatic.todoList.postgres;


import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecondTaskService {

	@Autowired
	private SecondTaskRepository secondTaskRepository;

	public SecondTaskEntity addTask(SecondTaskEntity taskEntity) {
		return secondTaskRepository.save(taskEntity);
	}
	
	public void deleteTask(Long id) {
		secondTaskRepository.deleteById(id);
	}
	
	public Iterable<SecondTaskEntity> findAllTasks(){
		return secondTaskRepository.findAll();
	}
	
	public SecondTaskEntity findTaskById(Long id) {
		return secondTaskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	public SecondTaskEntity updateTaskById(SecondTaskEntity taskRequest, Long id) {
		
		SecondTaskEntity task = secondTaskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task"));
		
		task.setTitle(taskRequest.getTitle());
		task.setDueDate(taskRequest.getDueDate());
		
		return secondTaskRepository.save(task);
		
	}
}
