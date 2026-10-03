package com.myportfolio.backend.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

/*
 * @RestControllerAdvice makes this class a
 * GLOBAL exception handler.
 *
 * It means we don't need try-catch in every controller.
 *
 * Any controller/service exception can come here
 * if we have an appropriate @ExceptionHandler.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * ----------------------------------------------------
     * ResourceNotFoundException
     * ----------------------------------------------------
     *
     * Example:
     *
     * GET /api/profile/100
     *
     * If profile 100 doesn't exist,
     * ResourceNotFoundException will be thrown.
     *
     * HTTP Status = 404 NOT FOUND
     */

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /*
     * ----------------------------------------------------
     * DuplicateResourceException
     * ----------------------------------------------------
     *
     * Example:
     *
     * User tries to create a profile with an email
     * that already exists.
     *
     * HTTP Status = 409 CONFLICT
     */

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException ex,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
    /*
     * ----------------------------------------------------
     * BadRequestException
     * ----------------------------------------------------
     *
     * Example:
     *
     * PATCH request contains an unsupported field.
     *
     * HTTP Status = 400 BAD REQUEST
     */
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleFileUploadException(
            FileUploadException ex,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "File Upload Error",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }



}
