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

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    // Read the URL of the external API from properties file.
    @Value("${userservice.url}")
    String apiUrl;

    /**
     * Retrieves user information by making a GET request to
     * the specified API endpoint.
     *
     * @param userID The UUID of the user whose information is to be retrieved.
     * @return A ResponseEntity containing the response from the server, which may
     *         include user data
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> getUser(UUID userID) {
        log.info("getUser() is called: {}", userID);

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
     * Creates a new user by making a POST request to the
     * specified API endpoint.
     *
     * @param user The User object representing the user to be created.
     * @return A ResponseEntity containing the response from the server, which may
     *         include user data
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> create(User user) {
        log.info("create() is called: {}", user.getID());

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
     * Deletes a user by making a DELETE request to the specified
     * API endpoint.
     *
     * @param userID The UUID of the user to be deleted.
     * @return A ResponseEntity containing the response from the server, which may
     *         include success information
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> delete(UUID userID) {
        log.info("delete() is called: {}", userID);

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
     * Replaces an existing user with a new user representation
     * by making a PUT request
     * to the specified API endpoint.
     *
     * @param user The User object representing the new user data.
     * @return A ResponseEntity containing the response from the server, which may
     *         include updated user data
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
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

    /**
     * Updates user information by making a PUT request to the
     * specified API endpoint.
     *
     * @param user The User object representing the updated user data.
     * @return A ResponseEntity containing the response from the server, which may
     *         include updated user data
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
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
     * Retrieves a list of user Data Transfer Objects by
     * making a GET request to the
     * specified API endpoint.
     *
     * @return A ResponseEntity containing the response from the server, which may
     *         include a list of user DTOs
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
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
