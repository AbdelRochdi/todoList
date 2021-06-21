package com.pragmatic.todoList.entities.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.pragmatic.todoList.entities.task.TaskEntity;

@Entity
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Please enter your first name")
	private String firstName;
	@NotBlank(message = "Please enter your last name")
	private String lastName;
	@Email(message = "Please provide a valid email")
	@Column(updatable = false, unique = true)
	private String email;
	@NotBlank(message = "Please enter a valid password")
	@Size(min = 8, message = "Your password must have at least 8 caracters")
	private String password;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userEntity")
	private List<TaskEntity> tasksList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void addTask(TaskEntity taskEntity) {
		if (tasksList == null) {
			tasksList = new ArrayList<TaskEntity>();
		}

		tasksList.add(taskEntity);

		taskEntity.setUserEntity(this);
	}

	public List<TaskEntity> getTasksList() {
		return tasksList;
	}

	public void setTasksList(List<TaskEntity> tasksList) {
		this.tasksList = tasksList;
	}

}
