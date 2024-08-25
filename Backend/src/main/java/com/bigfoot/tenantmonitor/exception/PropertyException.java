package com.bigfoot.tenantmonitor.exception;

public class PropertyException extends GenericException {
    public PropertyException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
