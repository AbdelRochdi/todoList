package com.pragmatic.todoList.mysql.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pragmatic.todoList.mysql.entities.TaskEntity;

class TaskServiceImplTest {

	TaskServiceImpl taskServiceImpl;

	@BeforeEach
	void init() {
		taskServiceImpl = new TaskServiceImpl();
	}
	
	@Test
	void testCheckDueDate() throws ParseException  {
		
		TaskEntity task = new TaskEntity();
		task.setTitle("test task");
		task.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-07-22"));
		
		boolean expected = true;
		boolean actual = taskServiceImpl.checkDueDate(task.getDueDate());
		
		assertEquals(expected, actual, "the method should check if it's past dueDate");

		
	}

}
