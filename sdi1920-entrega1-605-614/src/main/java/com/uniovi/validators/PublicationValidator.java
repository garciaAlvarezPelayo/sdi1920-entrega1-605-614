package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Publication;

@Component
public class PublicationValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return Publication.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Publication publication = (Publication) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty");
		if(!publication.getImage().isEmpty()) {
			String extension = getExtension(publication.getImage().getContentType());
			if(!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg")) {
				errors.rejectValue("image", "Error.publication.image.invalid");
			}
		}
	}
	
	private String getExtension(String contentType) {
		return contentType.substring(contentType.lastIndexOf("/") + 1);
	}
}
