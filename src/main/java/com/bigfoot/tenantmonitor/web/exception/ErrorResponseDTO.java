package com.bigfoot.tenantmonitor.web.exception;

import org.springframework.http.HttpStatus;


public record ErrorResponseDTO(
        String message,
        int httpStatusCode,
        HttpStatus httpStatus,
        int errorCode,
        String timestamp
) {}