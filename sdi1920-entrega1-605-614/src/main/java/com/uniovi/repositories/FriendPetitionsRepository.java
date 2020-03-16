package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.FriendPetition;
import com.uniovi.entities.User;

public interface FriendPetitionsRepository extends CrudRepository<FriendPetition, Long>{

	Page<FriendPetition> findAll(Pageable pageable);
	
	@Query("SELECT u.email FROM User u JOIN u.petitionsArrived p WHERE p.sendUser.id=?1")
	List<String> findEmailsById(Long id); 
	
	@Query("SELECT u.email FROM User u JOIN u.petitionsSent p WHERE p.arriveUser.id=?1 and p.hasAccepted=false")
	Page<String> findByArriveUser(Long id, Pageable pageable);

	@Query("SELECT p FROM FriendPetition p WHERE p.sendUser.id=?1 AND p.arriveUser.id=?2")
	FriendPetition findBySendUserAndArriveUser(Long sendUserId, Long arriveUserId);

	@Query("SELECT u FROM User u JOIN u.petitionsSent p WHERE p.arriveUser.id=?1 and p.hasAccepted=true ")
	List<User> findUserBySendUserHasAccepted(Long id);
	
	@Query("SELECT u FROM User u JOIN u.petitionsArrived p WHERE p.sendUser.id=?1 and p.hasAccepted=true ")
	List<User> findUserByArriveUserHasAccepted(Long id);
}