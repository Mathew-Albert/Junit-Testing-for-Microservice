package com.ust.userservice.exception;

public class UserNotFoundException extends  Exception{

    public UserNotFoundException(String message) {
        super(message);
    }
}
