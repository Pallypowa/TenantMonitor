package com.bigfoot.tenantmonitor.domain.exception;

public class InvalidTokenException extends GenericException {
    public InvalidTokenException(String message) {
        super(ErrorCode.InvalidToken, message);
    }
}
