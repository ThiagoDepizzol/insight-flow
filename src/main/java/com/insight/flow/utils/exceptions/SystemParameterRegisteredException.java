package com.insight.flow.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SystemParameterRegisteredException extends RuntimeException {

    public SystemParameterRegisteredException(final String message) {
        super(message);
    }

}

