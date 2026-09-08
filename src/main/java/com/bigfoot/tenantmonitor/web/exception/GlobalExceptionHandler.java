package com.bigfoot.tenantmonitor.web.exception;

import com.bigfoot.tenantmonitor.domain.exception.ErrorCode;
import com.bigfoot.tenantmonitor.domain.exception.IncorrectLoginException;
import com.bigfoot.tenantmonitor.domain.exception.InvalidTokenException;
import com.bigfoot.tenantmonitor.domain.exception.PropertyException;
import com.bigfoot.tenantmonitor.domain.exception.UserAlreadyExistsException;
import com.bigfoot.tenantmonitor.domain.exception.UserNotFoundException;
import com.bigfoot.tenantmonitor.domain.exception.VerificationTokenInvalidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExists(UserAlreadyExistsException e){
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getErrorCodes().getErrorCode()));
    }

    @ExceptionHandler(IncorrectLoginException.class)
    public ResponseEntity<ErrorResponseDTO> handleIncorrectLogin(IncorrectLoginException e){
        return ResponseEntity
                .status(401)
                .body(buildErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, e.getErrorCodes().getErrorCode()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidToken(InvalidTokenException e){
        return ResponseEntity
                .status(401)
                .body(buildErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED, e.getErrorCodes().getErrorCode()));
    }

    @ExceptionHandler(PropertyException.class)
    public ResponseEntity<ErrorResponseDTO> handlePropertyError(PropertyException e){
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getErrorCodes().getErrorCode()));
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFound(UserNotFoundException e){
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getErrorCodes().getErrorCode()));
    }


    @ExceptionHandler(VerificationTokenInvalidException.class)
    public ResponseEntity<ErrorResponseDTO> handleVerificationTokenInvalid(VerificationTokenInvalidException e){
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getErrorCodes().getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidity(MethodArgumentNotValidException e){
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(Arrays.toString(e.getDetailMessageArguments()), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, ErrorCode.ValidityError.getErrorCode()));
    }

    private ErrorResponseDTO buildErrorResponse(String message, int statusCode, HttpStatus status, int errorCode){
        return new ErrorResponseDTO(message, statusCode, status, errorCode, LocalDateTime.now().toString());
    }

}
