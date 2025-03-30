package com.bigfoot.tenantmonitor.exception;

public enum ErrorCode {
    UsernameOrPwIncorrect(1000),
    UserAlreadyExists(1001),
    InvalidToken(1002),
    PropertyDoesNotExist(1003),
    PropertyAlreadyTaken(1004),
    ValidityError(1005),
    UserNotFound(1006),
    VerificationTokenInvalid(1007)
    ;

    private final Integer errorCode;

    ErrorCode(Integer error_code) {
        this.errorCode = error_code;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}
