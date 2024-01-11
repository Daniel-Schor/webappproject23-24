package dev.eventplaner.service;

import dev.eventplaner.model.ApiError;
import dev.eventplaner.model.Event;
import dev.eventplaner.model.EventDTO;
import dev.eventplaner.model.User;
import dev.eventplaner.model.UserDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.fasterxml.jackson.core.type.TypeReference;

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
    public String getUser(UUID userID) {
        log.info("get user by userID: {}", userID);
        // User user = userRepository.get(userID);
        // if (user == null) {
        // log.warn("User with ID {} not found", userID);
        // }
        // return user;
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + userID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response.getBody().toString();
    }

    /**
     * Creates a new user.
     *
     * @param user The User object to create.
     */
    public String create(User user) {
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
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response.getBody().toString();
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userID The ID of the user to delete.
     */
    public String delete(UUID userID) {
        log.info("delete userID: {}", userID);
        // if (userRepository.get(userID) == null) {
        // log.warn("User with ID {} not found", userID);
        // }
        // if (userRepository.get(userID) != null) {
        // log.warn("User with ID {} found", userID);
        // log.info("User Deleted: {}", userID);
        // return userRepository.remove(userID);
        // }
        // return null;
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + userID;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response.getBody().toString();
    }

    /**
     * Updates a user.
     *
     * @param user The User object to update.
     */
    public String update(User user) {
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
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response.getBody().toString();
    }

    /**
     * Retrieves all users.
     *
     * @return An Collection of all User objects.
     */
    public String getAll() {
        log.info("getAllUsers");

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response.getBody().toString();
    }

    public String getAllDTO() {
        log.info("get all Users as DTO");

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<String>(apiError.toString(), apiError.getStatus());
        }

        ObjectMapper mapper = new ObjectMapper();
        Collection<User> values = new ArrayList<>();

        try {
            values = mapper.readValue(response.getBody(), new TypeReference<Collection<User>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Collection<UserDTO> usersDTO = new ArrayList<>();
        if (values != null) {
            for (User user : values) {
                usersDTO.add(new UserDTO(user));
            }
        }
        return convertCollectionToJson(usersDTO);
    }

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
