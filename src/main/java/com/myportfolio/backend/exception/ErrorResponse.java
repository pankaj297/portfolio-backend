package com.myportfolio.backend.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * This class represents the common structure
 * of every error response returned by our API.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /*
     * Time when the error occurred.
     */
    private LocalDateTime timestamp;

    /*
     * HTTP status code.
     *
     * Example:
     * 404 = Not Found
     * 400 = Bad Request
     * 409 = Conflict
     * 500 = Internal Server Error
     */
    private int status;

    /*
     * Short name of the error.
     *
     * Example:
     * "Not Found"
     * "Bad Request"
     * "Conflict"
     */
    private String error;

    /*
     * Actual message explaining the problem.
     */
    private String message;

    /*
     * API URL where the error occurred.
     */
    private String path;

}
