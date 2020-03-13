package com.uniovi.services;

import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private SecurityService securityService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	

	@PostConstruct
	public void init() {
	}

	public Page<User> getUsers(Pageable pageable) {
		Page<User> users;
		users = usersRepository.findAll(pageable);
		return users;
	}

	public User getUser(Long id) {
		return usersRepository.findById(id).get();
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public void deleteUser(Long id) {
		usersRepository.deleteById(id);
	}

	public Page<User> getUsersFor(Pageable pageable) {
		Page<User> users;
		users = usersRepository.searchUsersFor(securityService.getCurrentUserEmail(), pageable);
		return users;
	}

	public User getCurrentUser() {
		return getUserByEmail(securityService.getCurrentUserEmail());
	}

	public Page<User> searchUserByEmailNameAndSurnameFor(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		users = usersRepository.searchByEmailNameAndSurnameFor(securityService.getCurrentUserEmail(), searchText, pageable);
		return users;
	}
}
