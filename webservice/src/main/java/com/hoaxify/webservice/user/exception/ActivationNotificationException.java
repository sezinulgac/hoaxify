package com.hoaxify.webservice.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.hoaxify.webservice.shared.Messages;

public class ActivationNotificationException extends RuntimeException {
    public ActivationNotificationException() {
    super(Messages.getMessageForLocale("hoaxify.create.user.email.failure", LocaleContextHolder.getLocale()));
    }
    
}
