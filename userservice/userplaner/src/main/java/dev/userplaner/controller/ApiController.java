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

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

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
     * Retrieves a list of all users.
     *
     * This method is mapped to the GET request at '/users' and returns all
     * available users.
     * It produces a response in JSON format. The actual retrieval of users is
     * delegated to the
     * userService's 'getAllDTO' method.
     *
     * @return A ResponseEntity containing a list of all users in JSON format. The
     *         response
     *         includes the appropriate HTTP status code based on the success or
     *         failure of the
     *         user retrieval operation.
     */
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getAllUsers() {
        log.info("POST localhost:8083/users -> getAllUsers() is called: {}");

        return userService.getAllDTO();
    }

    /**
     * Retrieves information for a specific user based on the provided user ID.
     *
     * <p>
     * This method is mapped to the GET request at '/users/{userID}' and returns
     * information
     * for the user with the specified ID. It produces a response in JSON format.
     * The actual retrieval
     * of user information is delegated to the userService's 'getUser' method.
     *
     * @param userID The unique identifier of the user to retrieve information for.
     * @return A ResponseEntity containing information for the specified user in
     *         JSON format. The
     *         response includes the appropriate HTTP status code based on the
     *         success or failure
     *         of the user retrieval operation.
     */
    @GetMapping(value = "/users/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getUser(@PathVariable("userID") UUID userID) {
        log.info("GET localhost:8083/users/{} -> getUser() is called: {}", userID);

        return userService.getUser(userID);
    }

    /**
     * Creates a new user based on the provided user data.
     *
     * This method is mapped to the POST request at '/users'. It is responsible for
     * creating
     * a new user using the details provided in the request body. The request and
     * response are
     * both in JSON format. The user creation process is handled by the
     * userService's 'create' method.
     *
     * @param user The User object containing the information for the new user.
     * @return A ResponseEntity indicating the outcome of the user creation process.
     *         The response includes the appropriate HTTP status code and any
     *         relevant
     *         user data in JSON format.
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
     * @param userID The UUID of the user to be replaced.
     * @param user   The User object containing the updated user data.
     * @return A ResponseEntity containing the response from the server, which may
     *         include updated user data
     *         or an error message in case of failure.
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

    /**
     * Updates an existing user with new user data provided in the request body by
     * making a PUT request.
     *
     * This method is mapped to the PUT request at '/users/update/{userID}' and is
     * responsible for updating
     * a user identified by the provided userID with the new user data provided in
     * the request body.
     * The response is in JSON format. The actual update process is handled by the
     * userService's 'updateUser'
     * method.
     *
     * @param userID The UUID of the user to be updated.
     * @param user   The User object containing the updated user data.
     * @return A ResponseEntity containing the response from the server, which may
     *         include updated user data
     *         or an error message in case of failure.
     */
    @PutMapping(value = "/users/update/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable("userID") UUID userID, @RequestBody User user) {
        log.info("PUT localhost:8083/users/update/{} -> updateUser() is called: {}", userID);

        return userService.updateUser(user.setID(userID));
    }

    /**
     * Deletes a user identified by its UUID.
     *
     * This method responds to a DELETE request at '/users/{userID}'. It is
     * responsible for deleting
     * the user corresponding to the given userID. The deletion process is delegated
     * to the userService's
     * 'delete' method. The method produces a response in JSON format.
     *
     * @param userID The UUID of the user to be deleted.
     * @return A ResponseEntity indicating the result of the delete operation,
     *         including the appropriate
     *         HTTP status code. The response may also include additional
     *         information about the deletion process.
     */
    @DeleteMapping(value = "/users/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteEvent(@PathVariable("userID") UUID userID) {
        log.info("DELETE localhost:8083/users/{} -> deleteEvent() is called: {}", userID);

        return userService.delete(userID);
    }

}
