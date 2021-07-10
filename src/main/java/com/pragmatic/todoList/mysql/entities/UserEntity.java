package com.pragmatic.todoList.mysql.entities;

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
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users", schema = "todolist")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, unique = true)
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
	private String encryptedPassword;
	@Column(nullable = false)
	private boolean isActive = false;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userEntity")
	private List<TaskEntity> tasksList;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("userEntity")
	private List<ConfirmationToken> confirmationTokens;

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

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<ConfirmationToken> getConfirmationTokens() {
		return confirmationTokens;
	}

	public void setConfirmationTokens(List<ConfirmationToken> confirmationTokens) {
		this.confirmationTokens = confirmationTokens;
	}

	public void addConfirmationToken(ConfirmationToken confirmationToken) {
		if (confirmationTokens == null) {
			confirmationTokens = new ArrayList<ConfirmationToken>();
		}

		confirmationTokens.add(confirmationToken);

		confirmationToken.setUserEntity(this);
	}
}
