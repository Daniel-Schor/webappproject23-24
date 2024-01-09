package dev.eventplaner.controller;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.eventplaner.model.User;
import dev.eventplaner.model.UserDTO;
import dev.eventplaner.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class ApiController {

    // Logger instance for this class, used to log system messages, warnings, and
    // errors.
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    // UserService
    // instance into this class.
    @Autowired
    private UserService userService;

    /**
     * Retrieves all users from the database.
     *
     * @return ResponseEntity containing a collection of UserDTO objects
     */
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Collection<UserDTO>> getAllUsers() {
        log.info("Get all users");
        Collection<UserDTO> users = userService.getAllDTO();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
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
    public ResponseEntity<User> getUser(@PathVariable("userID") UUID userID) {
        log.info("Get all users");
        User user = userService.getUser(userID);

        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
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
        String fullname = user.getFirstName() + user.getLastName();
        log.info("Create new user: ", fullname);
        if (fullname == null || fullname.isEmpty()) {
            String detail = "User name must not be null or empty";
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
            pd.setInstance(URI.create("/users"));
            pd.setTitle("User creation error");
            return ResponseEntity.unprocessableEntity().body(pd);
        }
        User createdUser = userService.create(user);
        return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
    }

    // neu Methode

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
    public ResponseEntity<User> updateUser(@PathVariable("userID") UUID userID, @RequestBody User user) {
        log.info("Update user: {}", userID);
        User updatedUser = userService.update(user);

        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }


}
