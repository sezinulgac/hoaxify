package com.hoaxify.webservice.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.hoaxify.webservice.shared.Messages;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super(Messages.getMessageForLocale("hoaxify.activate.user.invalid.token",LocaleContextHolder.getLocale() ));
    }

    
}
