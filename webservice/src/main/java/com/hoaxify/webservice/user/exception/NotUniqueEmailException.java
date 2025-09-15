package com.hoaxify.webservice.user.exception;

import java.util.Collections;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

import com.hoaxify.webservice.shared.Messages;

public class NotUniqueEmailException extends RuntimeException {
    public NotUniqueEmailException() {
        super(Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale()));
    }

    public Map<String, String> getValidationErrors() {
        return Collections.singletonMap("email", Messages.getMessageForLocale("hoaxify.constraint.email.notunique", LocaleContextHolder.getLocale()));
    }

}
