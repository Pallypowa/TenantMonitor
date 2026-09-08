package com.bigfoot.tenantmonitor.domain.exception;

public class GenericException extends RuntimeException {
    private final ErrorCode errorCode;

    public GenericException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }


    public ErrorCode getErrorCodes() {
        return errorCode;
    }
}
