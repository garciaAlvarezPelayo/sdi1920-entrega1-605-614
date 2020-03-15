package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FriendPetition {

	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private User sendUser;
	@ManyToOne
	private User arriveUser;
	private boolean hasAccepted;
	
	public FriendPetition() {}
	
	public FriendPetition(User sendUser, User arriveUser) {
		this.sendUser = sendUser;
		this.arriveUser = arriveUser;
		this.hasAccepted = false;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getSendUser() {
		return sendUser;
	}
	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}
	public User getArriveUser() {
		return arriveUser;
	}
	public void setArriveUser(User arriveUser) {
		this.arriveUser = arriveUser;
	}
	public boolean hasAccepted() {
		return hasAccepted;
	}
	public void setHasAccepted(boolean hasAccepted) {
		this.hasAccepted = hasAccepted;
	}
		
}
