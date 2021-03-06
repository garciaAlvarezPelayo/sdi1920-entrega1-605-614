package com.uniovi.entities;

import javax.persistence.*;

import java.util.Set; //A collection that contains no duplicate elements

@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String surname;
	private String password;
	@Transient
	private String passwordConfirm;
	private String role;
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private Set<Publication> publications;
	
	
	@OneToMany(mappedBy = "sendUser", cascade = CascadeType.ALL)
	private Set<FriendPetition> petitionsSent;
	
	@OneToMany(mappedBy = "arriveUser", cascade = CascadeType.ALL)
	private Set<FriendPetition> petitionsArrived;

	public User(String email, String name, String surname) {
		super();
		this.email = email;
		this.name = name;
		this.surname = surname;
	}

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return this.name + " " + this.surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Set<FriendPetition> getPetitionsSent() {
		return petitionsSent;
	}

	public void setPetitionsSent(Set<FriendPetition> petitionsSent) {
		this.petitionsSent = petitionsSent;
	}

	public Set<FriendPetition> getPetitionsArrived() {
		return petitionsArrived;
	}

	public void setPetitionsArrived(Set<FriendPetition> petitionsArrived) {
		this.petitionsArrived = petitionsArrived;
	}
	
}
