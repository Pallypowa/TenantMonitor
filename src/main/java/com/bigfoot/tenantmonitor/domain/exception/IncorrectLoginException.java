package com.bigfoot.tenantmonitor.domain.exception;

public class IncorrectLoginException extends GenericException {
    public IncorrectLoginException(String message) {
        super(ErrorCode.UsernameOrPwIncorrect, message);
    }
}
