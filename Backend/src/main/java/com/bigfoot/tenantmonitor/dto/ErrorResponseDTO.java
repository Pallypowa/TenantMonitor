package com.bigfoot.tenantmonitor.dto;


import org.springframework.http.HttpStatus;


public record ErrorResponseDTO(
        String message,
        int httpStatusCode,
        HttpStatus httpStatus,
        int errorCode,
        String timestamp
) {}