package com.myportfolio.backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
/*
 * This exception is used when a requested resource
 * does not exist in the database.
 *
 * Example:
 * GET /api/profile/10
 *
 * If profile with id 10 does not exist,
 * we throw this exception.
 */