package com.pragmatic.todoList.mysql.controllers;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pragmatic.todoList.mysql.entities.TaskEntity;
import com.pragmatic.todoList.mysql.services.TaskService;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;	

	@GetMapping("/all")
	public ResponseEntity<Iterable<TaskEntity>> getAllTasks() {
		Iterable<TaskEntity> tasks = taskService.findAllTasks();
		return new ResponseEntity<Iterable<TaskEntity>>(tasks, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<TaskEntity> createTask(@Valid @RequestBody TaskEntity taskEntity) {
		TaskEntity savedTask = taskService.addTask(taskEntity);
		return new ResponseEntity<TaskEntity>(savedTask, HttpStatus.CREATED);
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long taskId) {
		Optional<TaskEntity> task = taskService.findTaskById(taskId);
		if(task.isPresent())  return new ResponseEntity<TaskEntity>(task.get(), HttpStatus.OK);
		throw new EntityNotFoundException("");
	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable Long taskId) {
		taskService.deleteTask(taskId);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/{taskId}")
	public ResponseEntity<TaskEntity> updateTask(@PathVariable Long taskId, @RequestBody TaskEntity taskEntity){
		TaskEntity updatedTask = taskService.updateTaskById(taskEntity, taskId);
		return new ResponseEntity<TaskEntity>(updatedTask, HttpStatus.ACCEPTED);
		
	}
}
