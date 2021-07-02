package com.pragmatic.todoList.mysql.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragmatic.todoList.mysql.entities.TaskEntity;
import com.pragmatic.todoList.mysql.services.TaskService;
import com.pragmatic.todoList.mysql.services.UserService;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private TaskService taskService;
	
	@MockBean
	private UserService userService;

	@Test
	void testGetAllTasks() throws Exception {
		TaskEntity task = new TaskEntity();
		task.setTitle("test task");
		task.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-22"));
		
		List<TaskEntity> taskList = Arrays.asList(task);
		
	    given(taskService.findAllTasks()).willReturn(taskList);

	    mvc.perform(get("/api/tasks/all")
	    	      .contentType(MediaType.APPLICATION_JSON))
	    	      .andExpect(status().isOk())
	    	      .andExpect(jsonPath("$", hasSize(1)))
	    	      .andExpect(jsonPath("$[0].title", is(task.getTitle())));   
	}
	
	
	@Test
	void testCreateTask() throws Exception {
		TaskEntity task = new TaskEntity();
		task.setTitle("test task");
		task.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-22"));
		
		
	    given(taskService.addTask(task)).willReturn(task);

	    mvc.perform(
	    		MockMvcRequestBuilders.
	    		post("/api/tasks")
	    		.content(asJsonString(task))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                )
	    .andExpect(status().isCreated());
	    
	    
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}


	@Test
	void testGetTaskById() throws Exception {
		Long id = 3L; 
		TaskEntity task = new TaskEntity();
		task.setId(id);
		task.setTitle("test task");
		task.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-22"));
		
		given(taskService.findTaskById(3L)).willReturn(Optional.of(task));
		
		mvc.perform(get("/api/tasks" + "/" + id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title", is(task.getTitle())));
	}
	
	@Test
	void testDeleteTaskById() {
		
	}
	
	@Test
	void testUpdateTask() {
		
	}
	
	

}
