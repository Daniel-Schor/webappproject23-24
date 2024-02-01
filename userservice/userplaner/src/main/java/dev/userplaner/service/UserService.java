package dev.userplaner.service;

import dev.userplaner.model.User;
import dev.userplaner.model.UserDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class represents a service for managing user-related operations.
 */
@Service
public class UserService {

    // Logger instance for this class, used to log system messages, warnings, and
    // errors.
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Value("${repository.url}")
    String apiUrl;

    /**
     * Retrieves a user by their ID.
     *
     * @param userID The ID of the user to retrieve.
     * @return The User object corresponding to the given ID, or null if no such
     *         user exists.
     */
    public ResponseEntity<?> getUser(UUID userID) {
        log.info("get user by userID: {}", userID);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + userID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        return response;
    }

    /**
     * Creates a new user.
     *
     * @param user The User object to create.
     */
    public ResponseEntity<?> create(User user) {
        log.info("User Created: {}", user.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        return response;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userID The ID of the user to delete.
     */
    public ResponseEntity<?> delete(UUID userID) {
        log.info("delete userID: {}", userID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + userID;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        return response;
    }

    /**
     * Updates a user.
     *
     * @param user The User object to update.
     */
    public ResponseEntity<?> update(User user) {
        log.info("User Updated: {}", user.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + user.getID();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        return response;
    }

    /**
     * Retrieves all users.
     *
     * @return An Collection of all User objects.
     */
    public ResponseEntity<?> getAll() {
        log.info("getAllUsers");

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        return response;
    }

    /**
     * Retrieves all users as DTO (Data Transfer Object).
     * 
     * @return ResponseEntity<String> containing the response with the list of users as DTO in the body.
     */
    public ResponseEntity<String> getAllDTO() {
        log.info("get all Users as DTO");

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatus.NO_CONTENT){
                return response;
            }
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
            return response;
        }

        String body = response.getBody();
        Collection<User> values = User.collectionFromJson(body);

        log.info("values: {}", body);

        for (User user : values) {
            log.info("User: {}", user.getID());
        }

        Collection<UserDTO> usersDTO = new ArrayList<>();
        if (values != null) {
            for (User user : values) {
                UserDTO newUser = new UserDTO(user);
                log.info("newUser: {}", newUser.getUserID());
                usersDTO.add(newUser);
            }
        }
        response = new ResponseEntity<String>(convertCollectionToJson(usersDTO), HttpStatus.OK);
        return response;
    }

    /**
     * Converts a collection of UserDTO objects to a JSON string representation.
     *
     * @param usersDTO the collection of UserDTO objects to be converted
     * @return the JSON string representation of the collection
     */
    public String convertCollectionToJson(Collection<UserDTO> usersDTO) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(usersDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
