package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Publication;
import com.uniovi.entities.User;

public interface PublicationRepository extends CrudRepository<Publication, Long> {
	Page<Publication> findAll(Pageable pageable);

	Page<Publication> findAllByAuthor(Pageable pageable, User user);
}
