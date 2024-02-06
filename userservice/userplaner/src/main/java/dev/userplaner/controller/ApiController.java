package dev.userplaner.controller;

import java.net.URI;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.userplaner.model.User;

import dev.userplaner.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class ApiController {

    // Logger instance for this class, used to log system messages, warnings, and
    // errors.
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    // UserService
    // instance into this class.
    @Autowired
    private UserService userService;

    public ResponseEntity<?> checkProcessability(User user) {
        log.info("Check processability of user");
        String detail = User.isValid(user);
        if (detail != null) {
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
            pd.setInstance(URI.create("/users"));
            pd.setTitle("JSON Object Error");
            return ResponseEntity.unprocessableEntity().body(pd);
        }
        return null;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return ResponseEntity containing a collection of UserDTO objects
     */
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getAllUsers() {
        log.info("POST localhost:8083/users -> getAllUsers() is called: {}");

        return userService.getAllDTO();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userID the ID of the user to retrieve
     * @return the ResponseEntity containing the user if found, or no content if not
     *         found
     */
    @GetMapping(value = "/users/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getUser(@PathVariable("userID") UUID userID) {
        log.info("GET localhost:8083/users/{} -> getUser() is called: {}", userID);

        return userService.getUser(userID);
    }

    /**
     * Creates a new user.
     *
     * @param user The user object containing the user details.
     * @return ResponseEntity<?> The response entity containing the created user or
     *         an error message.
     */
    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody User user) {
        log.info("POST localhost:8083/users -> createUser() is called: {}", user.getID());

        ResponseEntity<?> response = checkProcessability(user);
        if (response == null) {
            return userService.create(user);
        }
        return response;
    }

    /**
     * Updates a user with the given user ID.
     *
     * @param userID The ID of the user to be updated.
     * @param user   The updated user object.
     * @return The ResponseEntity containing the updated user object if successful,
     *         or a not found response if the user does not exist.
     */
    @PutMapping(value = "/users/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> replaceUser(@PathVariable("userID") UUID userID, @RequestBody User user) {
        log.info("PUT localhost:8083/users/{} -> replaceUser() is called: {}", userID);

        ResponseEntity<?> response = checkProcessability(user);
        if (response == null) {
            return userService.replace(user.setID(userID));
        }
        return response;
    }

    // TODO javadoc
    @PutMapping(value = "/users/update/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable("userID") UUID userID, @RequestBody User user) {
        log.info("PUT localhost:8083/users/update/{} -> updateUser() is called: {}", userID);

        return userService.updateUser(user.setID(userID));
    }

    /**
     * Deletes a user event based on the provided userID.
     *
     * @param userID the ID of the user event to be deleted
     * @return a ResponseEntity representing the result of the deletion operation
     */
    @DeleteMapping(value = "/users/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteEvent(@PathVariable("userID") UUID userID) {
        log.info("DELETE localhost:8083/users/{} -> deleteEvent() is called: {}", userID);

        return userService.delete(userID);
    }

}
