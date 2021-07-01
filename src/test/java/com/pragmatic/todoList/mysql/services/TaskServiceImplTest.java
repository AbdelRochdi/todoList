package com.pragmatic.todoList.mysql.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskServiceImplTest {

	TaskServiceImpl taskServiceImpl;

	@BeforeEach
	void init() {
		taskServiceImpl = new TaskServiceImpl();
	}

	@Test
	void testAddNumbers() {

		int expected = 3;
		int actual = taskServiceImpl.addNumbers(1, 1);

		assertEquals(expected, actual);
	}

}
