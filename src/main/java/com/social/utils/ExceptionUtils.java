package com.social.utils;

import com.social.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ExceptionUtils {

    public ExceptionUtils() {
    }

    public static ApplicationException buildApplicationException(HttpStatus status, String message){
        return ApplicationException.builder()
                .status(status)
                .responseMessage(String.valueOf(message))
                .build();
    }
}