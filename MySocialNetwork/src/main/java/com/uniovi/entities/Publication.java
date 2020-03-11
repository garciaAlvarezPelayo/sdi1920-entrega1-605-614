package com.uniovi.entities;

import javax.persistence.*;

import java.util.Date;

@Entity
public class Publication {
	@Id
	@GeneratedValue
	private long id;
	private String title;
	private String text;
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;
	private Date publicationDate;

	public Publication(String title, String text) {
		super();
		this.title = title;
		this.text = text;
	}

	public Publication() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

}
