package dev.eventplaner.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.eventplaner.model.User;
import dev.eventplaner.model.UserDTO;
import dev.eventplaner.model.Event;
import dev.eventplaner.service.EventService;
import dev.eventplaner.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class ApiController {

    // Logger instance for this class, used to log system messages, warnings, and
    // errors.
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    // Autowired annotation is used to automatically inject the EventService and
    // UserService instance into this class.
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    /**
     * Retrieves all users.
     *
     * Mapped to the GET request at '/users', this method fetches a list of all
     * registered users. The response is provided in JSON format and is handled by
     * the userService.
     *
     * @return ResponseEntity containing a collection of all users in JSON format.
     */
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        log.info("GET localhost:8080/users -> getAllUsers() is called");

        ResponseEntity<?> response = userService.getAllDTO();

        return response;
    }

    /**
     * Retrieves a user's details based on their UUID.
     *
     * Mapped to a GET request at '/users/{userID}', this method fetches the
     * details of the user identified by the provided userID. The response is in
     * JSON format.
     *
     * @param userID The UUID of the user whose details are to be retrieved.
     * @return ResponseEntity containing the user's details in JSON format.
     */
    @GetMapping(value = "users/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("userID") UUID userID) {
        log.info("GET localhost:8080/users/{} -> getUser({}) is called", userID, userID);

        ResponseEntity<?> response = userService.getUser(userID);

        return response;
    }

    /**
     * Creates a new user.
     *
     * Mapped to the POST request at '/users', this method creates a new user with
     * the details provided in the request body. It consumes and produces JSON.
     *
     * @param user The User object containing the information for the new user.
     * @return ResponseEntity representing the outcome of the user creation
     *         operation.
     */
    @PostMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        String fullname = user.getFirstName() + " " + user.getLastName();
        log.info("POST localhost:8080/users -> createUser(Name: {}) is called", fullname);

        return userService.create(user);
    }

    /**
     * Replaces a user with updated information.
     *
     * Mapped to the PUT request at '/users/{userID}', this method updates the
     * details of a user identified by userID. The updated user information is
     * provided in JSON format in the request body. It consumes and produces JSON.
     *
     * @param userID The UUID of the user to be replaced, obtained from the URL.
     * @param user   The updated user data provided in the request body.
     * @return ResponseEntity containing the result of the update operation.
     */
    @PutMapping(value = "users/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> replaceUser(@PathVariable("userID") UUID userID, @RequestBody User user) {
        String fullname = user.getFirstName() + " " + user.getLastName();
        log.info("PUT localhost:8080/users/{} -> replaceUser({}, Name: {}) is called", userID, userID, fullname);
        ResponseEntity<?> response = userService.replace(user.setID(userID));

        return response;
    }

    /**
     * Partially updates the details of an existing user.
     *
     * This method, mapped to the PATCH request at '/users/{userID}', allows for the
     * partial
     * updating of a user's details. It consumes and produces JSON. The user to be
     * updated is
     * identified by the userID provided in the path, and the new details for the
     * user are
     * contained in the request body.
     *
     * @param userID The UUID of the user to be partially updated.
     * @param user   The User object containing the updated details.
     * @return ResponseEntity reflecting the result of the partial update operation.
     */
    @PatchMapping(value = "users/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable("userID") UUID userID, @RequestBody User user) {
        String fullname = user.getFirstName() + " " + user.getLastName();
        log.info("PATCH localhost:8080/users/{} -> updateUser({}, Name: {}) is called", userID, userID, fullname);
        ResponseEntity<?> response = userService.update(user.setID(userID));

        return response;
    }

    /**
     * Removes the User from a Event.
     *
     * Mapped to the POST request at '/events', this method is responsible for
     * creating a new event
     * using the details provided in the request body. It consumes and produces
     * JSON.
     *
     * @param event The Event object containing the details of the new event.
     * @return ResponseEntity reflecting the result of the event creation operation.
     */
    @PutMapping(value = "events/{eventID}/remove/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeUser(@PathVariable("eventID") UUID eventID, @PathVariable("userID") UUID userID) {
        log.info("PUT localhost:8080/events/{}/remove/{} -> removeUser({}, {}) is called", eventID, userID, eventID,
                userID);
        ResponseEntity<?> response = eventService.removeUser(eventID, userID);

        return response;
    }

    /**
     * Handles a POST request to create a new event.
     * Consumes and produces JSON.
     *
     * @param event The event to create.
     * @return A ResponseEntity containing the response from the EventService.
     */
    @PostMapping(value = "events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        log.info("POST localhost:8080/events -> createEvent(Name: {}) is called", event.getName());

        return eventService.create(event);
    }

    /**
     * Updates an existing event identified by its UUID.
     *
     * This method, mapped to the PUT request at '/events/{eventID}', updates an
     * event with new information.
     * It consumes and produces JSON. The event to be updated is identified by
     * eventID, and the new event details
     * are provided in the request body.
     *
     * @param eventID The UUID of the event to be updated.
     * @param event   The updated event data.
     * @return ResponseEntity indicating the result of the update operation.
     */
    @PutMapping(value = "events/{eventID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> replaceEvent(@PathVariable("eventID") UUID eventID, @RequestBody Event event) {
        log.info("PUT localhost:8080/events/{} -> replaceEvent({}, Name: {}) is called", eventID, event.getName());

        ResponseEntity<?> response = eventService.replace(event.setID(eventID));

        return response;
    }

    /**
     * Partially updates the details of an existing event.
     *
     * This method, mapped to the PATCH request at '/events/{eventID}', partially
     * updates an
     * event with new information provided in the request body. It is designed to
     * consume
     * and produce JSON. The target event is identified by eventID, and the new
     * details
     * for the event are passed in the request body.
     *
     * @param eventID The UUID of the event to be partially updated.
     * @param event   The event object containing the updated details.
     * @return ResponseEntity reflecting the result of the partial update operation.
     */
    @PatchMapping(value = "events/{eventID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEvent(@PathVariable("eventID") UUID eventID, @RequestBody Event event) {
        log.info("PATCH localhost:8080/events/{} -> updateEvent({}, Name: {}) is called", eventID, event.getName());

        ResponseEntity<?> response = eventService.update(event.setID(eventID));

        return response;
    }

    /**
     * Deletes an event identified by its UUID.
     *
     * Mapped to the DELETE request at '/events/{eventID}', this method is
     * responsible for deleting
     * the event corresponding to the given eventID. The deletion process is handled
     * by the eventService.
     *
     * @param eventID The UUID of the event to be deleted.
     * @return ResponseEntity reflecting the result of the delete operation.
     */
    @DeleteMapping(value = "events/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteEvent(@PathVariable("eventID") UUID eventID) {
        log.info("DELETE localhost:8080/events/{} -> deleteEvent({}) is called", eventID, eventID);

        ResponseEntity<?> response = eventService.delete(eventID);

        return response;
    }

    /**
     * Adds a user as a participant to an event.
     *
     * This method, mapped to the PUT request at '/events/{eventID}/add/{userID}',
     * adds the specified user
     * as a participant to the specified event. It checks if the user exists before
     * adding to the event.
     *
     * @param eventID The UUID of the event to add the participant to.
     * @param userID  The UUID of the user to be added as a participant.
     * @return ResponseEntity indicating the outcome of the operation.
     *         Returns 'User not found' with HttpStatus.NOT_FOUND if the user does
     *         not exist,
     *         otherwise reflects the outcome of the addUser operation.
     */
    @PutMapping(value = "events/{eventID}/add/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addParticipant(@PathVariable("eventID") UUID eventID,
            @PathVariable("userID") UUID userID) {
        log.info("PUT localhost:8080/events/{}/add/{} -> addParticipant({}, {}) is called", eventID, userID, eventID,
                userID);

        ResponseEntity<?> response;
        if (userService.getUser(userID).getStatusCode() == HttpStatus.NOT_FOUND) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else {
            response = eventService.addUser(eventID, userID);
        }

        return response;
    }

    /**
     * Deletes a user identified by UUID.
     *
     * Mapped to the DELETE request at '/users/{userID}', this method removes the
     * specified user.
     * It first removes the user from all events and then deletes the user record.
     *
     * @param userID The UUID of the user to be deleted.
     * @return ResponseEntity reflecting the outcome of the delete operation.
     */
    @DeleteMapping(value = "users/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable("userID") UUID userID) {
        log.info("PUT localhost:8080/users/{} -> deleteUser({})", userID, userID);

        eventService.removeUserFromAllEvents(userID);
        ResponseEntity<?> response = userService.delete(userID);

        return response;
    }

    /**
     * Retrieves all events.
     *
     * This method, mapped to the GET request at '/events', fetches all available
     * events.
     * It delegates the fetching process to the eventService.
     *
     * @return ResponseEntity containing a collection of all events in JSON format.
     */
    @GetMapping(value = "events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEvents() {
        log.info("GET localhost:8080/events -> getAllEvents() is called");

        ResponseEntity<?> response = eventService.getAllDTO();

        return response;
    }

    /**
     * Retrieves the details of a specific event identified by its UUID.
     *
     * This method is mapped to the GET request at '/events/{eventID}' and is
     * responsible for fetching
     * the details of a particular event. The event is identified using the eventID
     * passed as a path variable.
     *
     * @param eventID The UUID of the event to be retrieved.
     * @return A ResponseEntity containing the event details.
     *         The response body contains the event data in JSON format if the event
     *         is found.
     *         If the event is not found, the ResponseEntity reflects the
     *         appropriate HTTP status code.
     */
    @GetMapping(value = "events/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEvent(@PathVariable("eventID") UUID eventID) {
        log.info("GET localhost:8080/events/{} -> getEvent({}) is called", eventID, eventID);

        ResponseEntity<?> response = eventService.getEvent(eventID);

        return response;
    }

    /**
     * Retrieves the list of participants for a specific event.
     *
     * Mapped to the GET request at '/events/{eventID}/participants', this method
     * fetches the participants
     * of an event identified by eventID. The participants are returned as a
     * collection of UserDTO objects.
     * If the event is not found, it returns the corresponding HTTP status.
     *
     * @param eventID The UUID of the event for which to retrieve participants.
     * @return ResponseEntity containing a collection of UserDTOs if participants
     *         are found,
     *         or the appropriate HTTP status if the event is not found or has no
     *         participants.
     */
    @GetMapping(value = "events/{eventID}/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEventParticipants(@PathVariable("eventID") UUID eventID) {
        log.info("GET localhost:8080/events/{}/participants -> getEventParticipants({}) is called", eventID, eventID);

        ResponseEntity<?> eventResponse = eventService.getEvent(eventID);
        if (eventResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            return eventResponse;
        }

        Event event = Event.eventFromJson(eventResponse.getBody().toString());

        Set<UUID> participantIDs = event.getParticipants().keySet();
        if (participantIDs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Collection<UserDTO> participants = new ArrayList<>();

        for (UUID userID : participantIDs) {
            User user = User.userFromJson(userService.getUser(userID).getBody().toString());
            UserDTO userDTO = new UserDTO(user);
            participants.add(userDTO);
        }

        return ResponseEntity.ok(participants);
    }

    /**
     * Rates an event by a user with a specified rating.
     *
     * Mapped to a PUT request at '/events/{eventID}/{userID}/{rating}', this method
     * allows a user to rate an event.
     * The method delegates to eventService to handle the rating process.
     *
     * @param eventID The UUID of the event.
     * @param userID  The UUID of the user rating the event.
     * @param rating  The integer rating value.
     * @return ResponseEntity indicating the outcome of the operation.
     */
    @PutMapping(value = "events/{eventID}/{userID}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> rateEvent(@PathVariable("eventID") UUID eventID, @PathVariable("userID") UUID userID,
            @PathVariable("rating") int rating) {
        log.info("PUT localhost:8080/events/{}/{}/{} -> rateEvent({}, {}, {}) is called", eventID, userID, rating,
                eventID, userID, rating);

        ResponseEntity<?> response = eventService.addRating(eventID, userID, rating);

        return response;
    }
}