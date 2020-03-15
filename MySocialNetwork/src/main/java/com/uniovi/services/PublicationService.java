package com.uniovi.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	public void addPublication(Publication publication, MultipartFile image) {
		addPublication(publication);
		try {
			String extension = getExtension(image.getContentType());
			InputStream is = image.getInputStream();
			Files.copy(is, Paths.get("src/main/resources/static/img/" + publication.getId() + "." + extension),
					StandardCopyOption.REPLACE_EXISTING);
			publication.setImagePath(publication.getId() + "." + extension);
			publicationRepository.save(publication);
		} catch (IOException ioe) {
			System.err.println("Could not upload file " + image.getOriginalFilename());
		}

	}

	private String getExtension(String contentType) {
		return contentType.substring(contentType.lastIndexOf("/") + 1);
	}

	public Page<Publication> getPublicationsForUser(Pageable pageable) {
		Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
		publications = publicationRepository.findAllByAuthor(pageable, usersService.getCurrentUser());
		return publications;
	}

}