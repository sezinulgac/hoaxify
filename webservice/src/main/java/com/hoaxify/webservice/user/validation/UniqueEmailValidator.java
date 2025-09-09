package com.hoaxify.webservice.user.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.hoaxify.webservice.user.User;
import com.hoaxify.webservice.user.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       User inDB = userRepository.findByEmail(value);
         return inDB == null;

    }
    
}
