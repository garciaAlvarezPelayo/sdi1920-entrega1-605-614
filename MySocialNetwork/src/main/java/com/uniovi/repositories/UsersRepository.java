package com.uniovi.repositories;

import com.uniovi.entities.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);

	@Query("select r from User r where (LOWER(r.surname) like lower(?1) or lower(r.name) like lower(?1))")
	Page<User> searchBySurnameAndName(Pageable pageable, String searchText);

	Page<User> findAll(Pageable pageable);

}
