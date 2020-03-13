package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entities.FriendPetition;
import com.uniovi.entities.User;
import com.uniovi.repositories.FriendPetitionsRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class FriendPetitionsService {

	@Autowired	
	private FriendPetitionsRepository friendPetitionsRepository;
	@Autowired
	private UsersRepository usersRepository;
	
	public void addFriendPetition(FriendPetition fp) {
		friendPetitionsRepository.save(fp);
	}
	
	public Page<FriendPetition> getFriendPetitions(Pageable pageable) {
		return friendPetitionsRepository.findAll(pageable);
	}
	
	public List<String> getEmailsById(Long id){
		return friendPetitionsRepository.findEmailsById(id);
	}
	
	public Page<String> getPetitionsOfUser(Long id, Pageable pageable){
		return friendPetitionsRepository.findByArriveUser(id, pageable);
	}

	public void acceptPetition(User currentUser, String email) {
		User user = usersRepository.findByEmail(email);
		FriendPetition fp = friendPetitionsRepository.findBySendUserAndArriveUser(user.getId(), currentUser.getId());
		fp.setHasAccepted(true);
		friendPetitionsRepository.save(fp);
	}
	
}
