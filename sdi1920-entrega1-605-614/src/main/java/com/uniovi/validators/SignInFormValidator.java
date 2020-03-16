package com.uniovi.validators;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class SignInFormValidator implements Validator {
	
	@Autowired
	private UsersService usersService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
		if (usersService.getUserByEmail(user.getEmail()) == null) {
			errors.rejectValue("email", "Error.wrong.signin.email");
		}
		if (usersService.getUserByEmail(user.getEmail()).getPassword() != user.getPassword()) {
			errors.rejectValue("password", "Error.wrong.signin.password");
		}
	}
}
