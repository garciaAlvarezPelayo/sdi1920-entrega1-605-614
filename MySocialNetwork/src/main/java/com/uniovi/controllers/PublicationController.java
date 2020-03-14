package com.uniovi.controllers;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.uniovi.entities.*;
import com.uniovi.services.PublicationService;
import com.uniovi.services.UsersService;

@Controller
public class PublicationController {
	
	@Autowired
	private PublicationService publicationService;
	@Autowired
	private UsersService usersService;
	
	@RequestMapping("/publication/list")
	public String getListado(Model model, Pageable pageable) {
		Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
		publications = publicationService.getPublicationsForCurrentUser(pageable);
		model.addAttribute("publicationList", publications.getContent());
		model.addAttribute("page", publications);
		return "publication/list";
	}
	
	@RequestMapping(value = "/users/publication/list/{id}", method=RequestMethod.POST)
	public String getList(Model model, @PathVariable Long id, Pageable pageable) {
		Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
		User user = usersService.getUser(id);
		publications = publicationService.getPublicationsForUser(user ,pageable);
		model.addAttribute("publicationList", publications.getContent());
		model.addAttribute("page", publications);
		return "publication/list";
	}

	@RequestMapping(value = "/publication/add")
	public String addPublication(Model model) {
		model.addAttribute("publication", new Publication());
		return "publication/add";
	}
	
	@RequestMapping(value = "/publication/add", method = RequestMethod.POST)
	public String setMark(@ModelAttribute Publication publication) {
		publicationService.addPublication(publication);
		return "redirect:/publication/list";
	}
}


