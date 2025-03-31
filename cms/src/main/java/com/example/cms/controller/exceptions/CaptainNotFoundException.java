package com.example.cms.controller.exceptions;

public class CaptainNotFoundException extends RuntimeException {
    public CaptainNotFoundException(String userID) {
        super("Could not find captain " + userID);
    }
}
