package com.hoaxify.webservice.user.dto;


import com.hoaxify.webservice.user.User;
import com.hoaxify.webservice.user.validation.UniqueEmail;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreate(
@NotBlank(message = "{hoaxify.constraint.username.notblank}")
    @Size(min = 4, max = 250)
    String username,

@NotBlank
    @Email
    @UniqueEmail
    String email,
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}", message = "{hoaxify.constraint.password.pattern}")
    String password
    ) {
        public User toUser(){
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            return user;
        }

}
