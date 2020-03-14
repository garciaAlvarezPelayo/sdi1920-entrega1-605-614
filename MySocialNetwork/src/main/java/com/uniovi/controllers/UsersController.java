package com.uniovi.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.FriendPetition;
import com.uniovi.entities.User;
import com.uniovi.services.FriendPetitionsService;
import com.uniovi.services.FriendsService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	@Autowired
	private SignUpFormValidator signInFormValidator;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private FriendPetitionsService friendPetitionsService;
	@Autowired
	private FriendsService friendsService;
	
	@RequestMapping("/users/list")
	public String getListado(Model model, Pageable pageable, @RequestParam(value = "", required = false) String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchUserByEmailNameAndSurnameFor(pageable, searchText);
		} else {
			users = usersService.getUsersFor(pageable);
		}
		
		User activeUser = usersService.getCurrentUser();
		List<String> friendPetitions = 	friendPetitionsService.getEmailsById(activeUser.getId()); 
		
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		model.addAttribute("activeUser", activeUser);
		model.addAttribute("friendPetitions", friendPetitions);
		model.addAttribute("friendList", friendsService.getFriends(activeUser.getId()));
		return "users/list";
	}


	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "/signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if(result.hasErrors()) {
			return "/signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:/users/list";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "/login";
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		return "/home";
	}
	
	@RequestMapping(value = "/users/list/petition/{id}", method=RequestMethod.POST)
	public String sendPetition(@PathVariable Long id) {
		User activeUser = usersService.getCurrentUser();
		User user = usersService.getUser(id);
		friendPetitionsService.addFriendPetition(new FriendPetition(activeUser, user));
		return "redirect:/users/list";
	}
	
	@RequestMapping(value = "/users/petition/list")
	public String getPetitionList(Model model, Pageable pageable) {
		User activeUser = usersService.getCurrentUser();
		Page<String> petitionList = friendPetitionsService.getPetitionsOfUser(activeUser.getId(), pageable);
		model.addAttribute("petitionList", petitionList.getContent());
		model.addAttribute("page", petitionList);
		return "users/petition/list";
	}
	
	@RequestMapping(value = "/users/petition/accept/{email}", method=RequestMethod.POST)
	public String acceptPetition(@PathVariable String email) {
		User activeUser = usersService.getCurrentUser();
		friendPetitionsService.acceptPetition(activeUser, email);
		return "redirect:/users/petition/list";
	}
	
	@RequestMapping(value="/users/friend/list")
	public String getFriendList(Model model, Pageable pageable) {
		User activeUser = usersService.getCurrentUser();
		Page<User> friendList = friendsService.getFriends(activeUser.getId(), pageable);
		model.addAttribute("friendList", friendList.getContent());
		model.addAttribute("page", friendList);
		return "users/friend/list";
	}
}

