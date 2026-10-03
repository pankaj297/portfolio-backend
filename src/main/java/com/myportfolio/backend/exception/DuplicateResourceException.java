package com.myportfolio.backend.exception;

public class DuplicateResourceException extends RuntimeException {
    
    public DuplicateResourceException(String message) {
        super(message);
    }
}
/*
 * This exception is used when a resource already exists
 * and we are trying to create another duplicate resource.
 *
 * Example:
 * Email already exists in MyProfile table.
 */