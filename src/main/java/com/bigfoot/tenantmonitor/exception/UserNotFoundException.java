package com.bigfoot.tenantmonitor.exception;

public class UserNotFoundException extends GenericException{
    public UserNotFoundException(String message) {
        super(ErrorCode.UserNotFound, message);
    }
}
