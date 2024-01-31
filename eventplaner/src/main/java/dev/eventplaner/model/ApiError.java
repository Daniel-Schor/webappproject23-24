package dev.eventplaner.model;

import org.springframework.http.HttpStatus;

/**
 * Represents an API error with a status and a message.
 */
public class ApiError {

    private HttpStatus status;
    private String message;

    /**
     * Default constructor for ApiError.
     */
    public ApiError() {
    }

    /**
     * Constructs an ApiError with the specified status and message.
     *
     * @param status  The status of the error.
     * @param message The message of the error.
     */
    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMesssage(String message) {
        this.message = message;
    }

}