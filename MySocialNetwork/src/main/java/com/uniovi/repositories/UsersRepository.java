package com.uniovi.repositories;

import com.uniovi.entities.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);

	Page<User> findAll(Pageable pageable);

	@Query("select r from User r where (r.email<>?1 and r.role<>'ROLE_ADMIN')")
	Page<User> searchUsersFor(String email, Pageable pageable);

	@Query("select r from User r where ((LOWER(r.surname) like lower(?2) or lower(r.name) like lower(?2) or lower(r.email) like lower(?2)) and r.email<>?1 and r.role<>'ROLE_ADMIN')")
	Page<User> searchByEmailNameAndSurnameFor(String currentUserEmail, String searchText, Pageable pageable);

}
