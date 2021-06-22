package com.pragmatic.todoList;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.pragmatic.todoList.postgres.SecondTaskEntity;
import com.pragmatic.todoList.postgres.SecondTaskRepository;

@Component
public class MyRunner implements CommandLineRunner {

	@Autowired
	private SecondTaskRepository secondTaskRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SecondTaskEntity secondTaskEntity = new SecondTaskEntity();	
		
		secondTaskEntity.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-06-22"));
		secondTaskEntity.setTitle("something");
		
		secondTaskRepository.save(secondTaskEntity);
		
		System.out.println("this is commandLineRunner");
		
	}

	
	
}
