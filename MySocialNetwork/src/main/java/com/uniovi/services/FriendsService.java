package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;
import com.uniovi.repositories.FriendPetitionsRepository;

@Service
public class FriendsService {

	@Autowired
	private FriendPetitionsRepository friendPetitionsRepository;
	
	public Page<User> getFriends(Long id, Pageable pageable) {
		List<User> users1 =  friendPetitionsRepository.findUserBySendUserHasAccepted(id);
		List<User> users2 =  friendPetitionsRepository.findUserByArriveUserHasAccepted(id);
		users1.addAll(users2);
		return new PageImpl<User>(users1);
	}
	
	public List<User> getFriends(Long id) {
		List<User> users1 =  friendPetitionsRepository.findUserBySendUserHasAccepted(id);
		List<User> users2 =  friendPetitionsRepository.findUserByArriveUserHasAccepted(id);
		users1.addAll(users2);
		return users1;
	}
	
}
