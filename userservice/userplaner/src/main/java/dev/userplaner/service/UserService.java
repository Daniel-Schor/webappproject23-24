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

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Value("${repository.url}")
    String apiUrl;

    /**
     * Retrieves event information by making a GET request to
     * the specified API endpoint,
     * using the provided eventID.
     *
     * @param eventID The UUID of the event to be retrieved.
     * @return A ResponseEntity containing the response from the server, which may
     *         include event data
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> getUser(UUID userID) {
        log.info("get user by userID: {}", userID);
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
     * Creates a new user by making a POST request to the specified API endpoint.
     *
     * @param user The User object representing the user to be created.
     * @return A ResponseEntity containing the response from the server, which may
     *         include created user data or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> create(User user) {
        log.info("User Created: {}", user.getID());

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
     * Deletes a user by making a DELETE request to the specified API endpoint,
     * using the provided userID.
     *
     * @param userID The UUID of the user to be deleted.
     * @return A ResponseEntity containing the response from the server, which may
     *         include success information or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> delete(UUID userID) {
        log.info("delete userID: {}", userID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + userID;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Replaces an existing user with new user data provided in the request body by
     * making a PUT request.
     *
     * This method is mapped to the PUT request at '/users/{userID}' and is
     * responsible for updating
     * a user identified by the provided userID with the new user data provided in
     * the request body.
     * The response is in JSON format. The actual replacement process is handled by
     * the userService's 'replace'
     * method.
     *
     * @param user The User object containing the updated user data.
     * @return A ResponseEntity containing the response from the server, which may
     *         include updated user data or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> replace(User user) {
        log.info("User Updated: {}", user.getID());

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
     * Retrieves information for all users by making a GET request to the specified
     * API endpoint.
     *
     * This method is mapped to the GET request at '/users' and is responsible for
     * retrieving
     * information for all users. The response is in JSON format.
     *
     * @return A ResponseEntity containing the response from the server, which may
     *         include information
     *         for all users or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> getAll() {
        log.info("getAllUsers");

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

    /**
     * Retrieves a list of User Data Transfer Objects (DTOs) by making a GET request
     * to the specified API endpoint.
     *
     * This method is mapped to the GET request at '/users' and is responsible for
     * retrieving
     * information for all users as DTOs. The response is initially checked for a
     * successful status code,
     * then converted into a collection of User objects. Finally, these User objects
     * are transformed
     * into UserDTOs, and the result is returned as a ResponseEntity in JSON format.
     *
     * @return A ResponseEntity containing the response from the server, which may
     *         include a list of UserDTOs or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
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
            if (response.getStatusCode() != HttpStatus.OK) {
                return response;
            }
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
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

    /**
     * Updates an existing user with the information provided in the given User
     * object.
     * If the user with the specified ID does not exist, a new user is created.
     *
     * @param newUser The User object containing the updated information.
     * @return A ResponseEntity containing the response from the
     *         server, which may
     *         include updated user data or an error message in case of
     *         failure.
     */
    public ResponseEntity<?> updateUser(User newUser) {
        log.info("update User: {}", newUser.getID());

        User user = User.userFromJson(getUser(newUser.getID()).getBody().toString());

        if (user == null) {
            return replace(newUser);
        }

        if (newUser.getFirstName() != null) {
            user.setFirstName(newUser.getFirstName());
        }
        if (newUser.getLastName() != null) {
            user.setLastName(newUser.getLastName());
        }
        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }
        if (newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }

        return replace(user);
    }
}
