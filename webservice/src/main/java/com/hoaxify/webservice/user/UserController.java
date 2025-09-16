package com.hoaxify.webservice.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hoaxify.webservice.error.ApiError;
import com.hoaxify.webservice.shared.GenericMessage;
import com.hoaxify.webservice.shared.Messages;
import com.hoaxify.webservice.user.dto.UserCreate;
import com.hoaxify.webservice.user.exception.ActivationNotificationException;
import com.hoaxify.webservice.user.exception.InvalidTokenException;
import com.hoaxify.webservice.user.exception.NotUniqueEmailException;

import jakarta.validation.Valid;


@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/api/v1/users")
    GenericMessage createUser(@Valid @RequestBody UserCreate user) {
System.err.println("---------" + LocaleContextHolder.getLocale().getLanguage());
        userService.save(user.toUser());
        String message = Messages.getMessageForLocale("hoaxify.create.user.success.message",LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @PatchMapping("/api/v1/users/{token}/active")
        GenericMessage activateUser(@PathVariable String token) {
System.err.println("---------" + LocaleContextHolder.getLocale().getLanguage());
userService.activateUser(token);
        String message = Messages.getMessageForLocale("hoaxify.activate.user.success.message",LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @GetMapping("/api/v1/users")
    Page<User> getUsers(Pageable pageable) {
        return userService.getUsers(pageable);
    }
    


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleMethodArgNotValidEx(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(400);
        Map<String, String> validationErrors = new HashMap<>();
        for (var fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return apiError;
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleNotUniqueEmailEx(NotUniqueEmailException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(400); 
        apiError.setValidationErrors(exception.getValidationErrors());

        return apiError;
    }
     @ExceptionHandler(ActivationNotificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleActivationNotificationException(ActivationNotificationException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(502); 
        return apiError;
    }

     @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleInvalidTokenException(InvalidTokenException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(400); 
        return apiError;
    }
}
