package com.bigfoot.tenantmonitor.domain.exception;

public class UserNotFoundException extends GenericException{
    public UserNotFoundException(String message) {
        super(ErrorCode.UserNotFound, message);
    }
}
