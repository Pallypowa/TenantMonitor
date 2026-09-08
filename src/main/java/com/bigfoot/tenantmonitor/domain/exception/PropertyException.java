package com.bigfoot.tenantmonitor.domain.exception;

public class PropertyException extends GenericException {
    public PropertyException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
