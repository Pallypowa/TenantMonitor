package com.bigfoot.tenantmonitor.domain.exception;

public class VerificationTokenInvalidException extends GenericException{
    public VerificationTokenInvalidException(String message) {
        super(ErrorCode.VerificationTokenInvalid, message);
    }
}
