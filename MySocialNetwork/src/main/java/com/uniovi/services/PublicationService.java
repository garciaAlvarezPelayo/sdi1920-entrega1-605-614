package com.uniovi.services;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Publication;
import com.uniovi.repositories.PublicationRepository;

@Service
public class PublicationService {
	@Autowired
	private PublicationRepository publicationRepository;
	@Autowired
	private UsersService usersService;

	public void addPublication(Publication publication) {
		publication.setAuthor(usersService.getCurrentUser());
		publication.setPublicationDate(new Date());
		publicationRepository.save(publication);
	}

	public Page<Publication> getPublicationsForUser(Pageable pageable) {
		Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
		publications = publicationRepository.findAllByAuthor(pageable, usersService.getCurrentUser());
		return publications;
	}

}