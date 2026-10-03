package com.myportfolio.backend.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
        }
}
/*
  * This exception is used when the client sends
  * a request that is syntactically valid but
  * logically invalid for our application.
  *
  * Example:
  * PATCH /api/profile/1
  *
  * {
  * "abc": "xyz"
  * }
  *
  * If "abc" is not a supported field,
  * we throw BadRequestException.
  */