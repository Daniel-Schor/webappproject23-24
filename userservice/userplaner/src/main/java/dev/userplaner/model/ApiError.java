package dev.userplaner.model;

/**
 * Represents the HTTP status codes defined in the HTTP specification.
 * These status codes are used to indicate the outcome of an HTTP request.
 * HttpStatus provides constants for each status code, allowing easy access
 * to the standard HTTP status codes.
 */
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

    /**
     * Get the HTTP status code of the error.
     * @return the HTTP status code
     */
    public HttpStatus getStatus() {
        return this.status;
    }

    /**
     * Set the HTTP status code of the error.
     * @param status the HTTP status code to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * Get the error message.
     * @return the error message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Set the error message.
     * @param message the error message to set
     */
    public void setMesssage(String message) {
        this.message = message;
    }

}