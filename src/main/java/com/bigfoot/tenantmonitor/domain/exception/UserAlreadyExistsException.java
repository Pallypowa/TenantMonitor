package com.bigfoot.tenantmonitor.domain.exception;

public class UserAlreadyExistsException extends GenericException{
    public UserAlreadyExistsException(String message) {
        super(ErrorCode.UserAlreadyExists, message);
    }
}
