package com.example.cms.controller.exceptions;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(Long userID) {
        super("Could not find user " + userID);
    }
}
