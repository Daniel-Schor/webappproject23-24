package dev.eventplaner.service;

import dev.eventplaner.model.User;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * This class provides services for managing users.
 * It includes methods for creating, retrieving, updating, and deleting users.
 */
@Service
public class UserService {

    // Logger instance for this class, used to log system messages, warnings, and
    // errors.
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    // Read the URL of the external API from properties file.
    @Value("${userservice.url}")
    String apiUrl;

    /**
     * Retrieves a specific user from the repository.
     *
     * This method sends a GET request to the repository to retrieve a user with the
     * specified ID. The URL for the repository is constructed by appending
     * "/users/" and the user ID to the base API URL. If the repository returns an
     * error, the method catches the HttpClientErrorException and returns a
     * ResponseEntity with the error message and status code from the exception.
     *
     * @param userID The ID of the user to be retrieved.
     * @return A ResponseEntity containing the response from the repository. If the
     *         retrieval was successful, the status code will be HttpStatus.OK and
     *         the body will contain the user. If an error occurred, the status code
     *         and error message from the HttpClientErrorException will be returned.
     */
    public ResponseEntity<?> getUser(UUID userID) {
        log.info("Eventplaner UserService -> getUser() is called: {}", userID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + userID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Creates a new user in the repository.
     *
     * This method sends a POST request to the repository to create a new user. The
     * URL for the repository is constructed by appending "/users" to the base API
     * URL. The user to be created is included in the body of the request. If the
     * repository returns an error, the method catches the HttpClientErrorException
     * and returns a ResponseEntity with the error message and status code from the
     * exception.
     *
     * @param user The user to be created.
     * @return A ResponseEntity containing the response from the repository. If the
     *         creation was successful, the status code will be HttpStatus.CREATED
     *         and the body will contain the ID of the created user. If an error
     *         occurred, the status code and error message from the
     *         HttpClientErrorException will be returned.
     */
    public ResponseEntity<?> create(User user) {
        log.info("Eventplaner UserService -> create() is called: {}", user.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);

        try {
            return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Deletes a specific user from the repository.
     *
     * This method sends a DELETE request to the repository to delete a user with
     * the specified ID. The URL for the repository is constructed by appending
     * "/users/" and the user ID to the base API URL. If the repository returns an
     * error, the method catches the HttpClientErrorException and returns a
     * ResponseEntity with the error message and status code from the exception.
     *
     * @param userID The ID of the user to be deleted.
     * @return A ResponseEntity containing the response from the repository. If the
     *         deletion was successful, the status code will be
     *         HttpStatus.NO_CONTENT. If an error occurred, the status code and
     *         error message from the HttpClientErrorException will be returned.
     */
    public ResponseEntity<?> delete(UUID userID) {
        log.info("Eventplaner UserService -> delete() is called: {}", userID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + userID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Updates a specific user in the repository.
     *
     * This method sends a PUT request to the repository to update a user with the
     * specified ID. The URL for the repository is constructed by appending
     * "/users/" and the user ID to the base API URL. The user to be updated is
     * included in the body of the request. If the repository returns an error, the
     * method catches the HttpClientErrorException and returns a ResponseEntity with
     * the error message and status code from the exception.
     *
     * @param user The user to be updated.
     * @return A ResponseEntity containing the response from the repository. If the
     *         update was successful, the status code will be HttpStatus.OK. If an
     *         error occurred, the status code and error message from the
     *         HttpClientErrorException will be returned.
     */
    public ResponseEntity<?> replace(User user) {
        log.info("replace() is called: {}", user.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + user.getID();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);

        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    // TODO javadoc
    public ResponseEntity<?> update(User user) {
        log.info("update() is called: {}", user.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/update/" + user.getID();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);
        
        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Retrieves all users from the repository.
     *
     * This method sends a GET request to the repository to retrieve all users. The
     * URL for the repository is constructed by appending "/users" to the base API
     * URL. If the repository returns an error, the method catches the
     * HttpClientErrorException and returns a ResponseEntity with the error message
     * and status code from the exception.
     *
     * @return A ResponseEntity containing the response from the repository. If the
     *         retrieval was successful, the status code will be HttpStatus.OK and
     *         the body will contain a list of all users. If an error occurred, the
     *         status code and error message from the HttpClientErrorException will
     *         be returned.
     */
    public ResponseEntity<?> getAllDTO() {
        log.info("getAllDTO() is called");

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

}
