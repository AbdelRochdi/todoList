package com.pragmatic.todoList.mysql.controllers;

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
import com.pragmatic.todoList.mysql.services.TaskServiceImpl;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private TaskServiceImpl taskService;

	@GetMapping("/all")
	public ResponseEntity<?> getAllTasks() {
		Iterable<TaskEntity> tasks = taskService.findAllTasks();
		return new ResponseEntity<Iterable<TaskEntity>>(tasks, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<?> createTask(@Valid @RequestBody TaskEntity taskEntity) {
		TaskEntity savedTask = taskService.addTask(taskEntity);
		return new ResponseEntity<TaskEntity>(savedTask, HttpStatus.CREATED);
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<?> getTaskById(@PathVariable Long taskId) {

		TaskEntity task = taskService.findTaskById(taskId);

		return new ResponseEntity<TaskEntity>(task, HttpStatus.OK);

	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<?> deleteTaskById(@PathVariable Long taskId) {

		taskService.deleteTask(taskId);

		return new ResponseEntity<String>("Project with Id "+taskId+" was deleted.", HttpStatus.NO_CONTENT);

	}
	
	@PutMapping("/{taskId}")
	public ResponseEntity<?> updateTask(@PathVariable Long taskId, @RequestBody TaskEntity taskEntity){
		
		TaskEntity updatedTask = taskService.updateTaskById(taskEntity, taskId);
		
		return new ResponseEntity<TaskEntity>(updatedTask, HttpStatus.ACCEPTED);
		
	}
}
