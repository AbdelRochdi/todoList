package com.pragmatic.todoList.postgres;


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

@Controller
@RequestMapping("/api/secondtasks")
public class SecondTaskController {

	@Autowired
	private SecondTaskService secondTaskService;

	@GetMapping("/all")
	public ResponseEntity<?> getAllSecondTasks() {
		Iterable<SecondTaskEntity> tasks = secondTaskService.findAllTasks();
		return new ResponseEntity<Iterable<SecondTaskEntity>>(tasks, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<?> createSecondTask(@Valid @RequestBody SecondTaskEntity taskEntity) {
		SecondTaskEntity savedTask = secondTaskService.addTask(taskEntity);
		return new ResponseEntity<SecondTaskEntity>(savedTask, HttpStatus.CREATED);
	}

	@GetMapping("/{taskId}")
	public ResponseEntity<?> getSecondTaskById(@PathVariable Long taskId) {

		SecondTaskEntity task = secondTaskService.findTaskById(taskId);

		return new ResponseEntity<SecondTaskEntity>(task, HttpStatus.OK);

	}

	@DeleteMapping("/{taskId}")
	public ResponseEntity<?> deleteSecondTaskById(@PathVariable Long taskId) {

		secondTaskService.deleteTask(taskId);

		return new ResponseEntity<String>("Project with Id "+taskId+" was deleted.", HttpStatus.NO_CONTENT);

	}
	
	@PutMapping("/{taskId}")
	public ResponseEntity<?> updateSecondTask(@PathVariable Long taskId, @RequestBody SecondTaskEntity taskEntity){
		
		SecondTaskEntity updatedTask = secondTaskService.updateTaskById(taskEntity, taskId);
		
		return new ResponseEntity<SecondTaskEntity>(updatedTask, HttpStatus.ACCEPTED);
		
	}
}
