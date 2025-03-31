package com.example.cms.controller.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userID) {
        super("Could not find user " + userID);
    }
}
