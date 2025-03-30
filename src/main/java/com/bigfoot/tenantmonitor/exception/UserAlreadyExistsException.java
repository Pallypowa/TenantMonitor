package com.bigfoot.tenantmonitor.exception;

public class UserAlreadyExistsException extends GenericException{
    public UserAlreadyExistsException(String message) {
        super(ErrorCode.UserAlreadyExists, message);
    }
}
