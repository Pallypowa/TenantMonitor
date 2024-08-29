package com.bigfoot.tenantmonitor.exception;

public class IncorrectLoginException extends GenericException {
    public IncorrectLoginException(String message) {
        super(ErrorCode.UsernameOrPwIncorrect, message);
    }
}
