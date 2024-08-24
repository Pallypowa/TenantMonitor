package com.bigfoot.tenantmonitor.exception;

public class InvalidTokenException extends GenericException {
    public InvalidTokenException(String message) {
        super(ErrorCode.InvalidToken, message);
    }
}
