package com.example.carcatalog.exceptions;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException(String message) {
        super(message);
    }

}