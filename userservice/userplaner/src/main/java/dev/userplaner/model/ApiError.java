package dev.userplaner.model;

import org.springframework.http.HttpStatus;

/**
 * Represents an API error with a status code and message.
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
     * Constructor for ApiError with specified status and message.
     * @param status the HTTP status code of the error
     * @param message the error message
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