package com.quick.sms.exception;

public class AuthenticationErrorException extends Exception {

    private static final long serialVersionUID = 1L;

    public AuthenticationErrorException(String message) {
        super(message);
    }
}
