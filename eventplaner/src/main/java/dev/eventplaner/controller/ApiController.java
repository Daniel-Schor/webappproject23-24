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

    // Logger instance for this class, used to log system messages, warnings, and errors.
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    // Autowired annotation is used to automatically inject the EventService and UserService instance into this class.
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    /**
     * Retrieves all users from the database.
     *
     * @return ResponseEntity containing a collection of UserDTO objects.
     */
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        log.info("GET localhost:8080/users -> getAllUsers() is called");

        ResponseEntity<?> response = userService.getAllDTO();

        return response;
    }

    /**
     * Retrieves a user by their ID
     *
     * @param userID the ID of the user to retrieve.
     * @return the ResponseEntity containing the user if found, or no content if not found.
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
     * @param user The user object containing the user details.
     * @return ResponseEntity<?> The response entity containing the created user or an error message.
     */
    @PostMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        String fullname = user.getFirstName() + " " + user.getLastName();
        log.info("POST localhost:8080/users -> createUser(Name: {}) is called", fullname);

        return userService.create(user);
    }

    /**
     * Updates a user with the given user ID.
     *
     * @param userID The ID of the user to be updated.
     * @param user   The updated user object.
     * @return The ResponseEntity containing the updated user object if successful, or a not found response if the user does not exist.
     */
    @PutMapping(value = "users/{userID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable("userID") UUID userID, @RequestBody User user) {
        String fullname = user.getFirstName() + " " + user.getLastName();
        log.info("PUT localhost:8080/users/{} -> updateUser({}, Name: {}) is called", userID, userID, fullname);
        ResponseEntity<?> response = userService.update(user.setID(userID));

        return response;
    }

    /**
     * Removes the User from a Event.
     *
     * @param eventID The ID of the event from which the user will be removed.
     * @param userID  The ID of the user to be removed from the event.
     * @return ResponseEntity<?> Returns the response entity that includes the status of the operation.
     */
    @PutMapping(value = "events/{eventID}/remove/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeUser(@PathVariable("eventID") UUID eventID, @PathVariable("userID") UUID userID) {
        log.info("PUT localhost:8080/events/{}/remove/{} -> removeUser({}, {}) is called", eventID, userID, eventID, userID);
        ResponseEntity<?> response = eventService.removeUser(eventID, userID);

        return response;
    }

    // TODO javadoc
    @PostMapping(value = "events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        log.info("POST localhost:8080/events -> createEvent(Name: {}) is called", event.getName());

        return eventService.create(event);
    }

    /**
     * Update an existing event.
     *
     * @param eventID The ID of the event to be updated.
     * @param event   An object of type Event that contains the updated event details.
     * @return ResponseEntity<Event> Returns the response entity that includes the updated event.
     */
    @PutMapping(value = "events/{eventID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEvent(@PathVariable("eventID") UUID eventID, @RequestBody Event event) {
        log.info("PUT localhost:8080/events/{} -> updateEvent({}, Name: {}) is called", eventID, event.getName());

        ResponseEntity<?> response = eventService.update(event.setID(eventID));

        return response;
    }

    /**
     * Delete an existing event.
     *
     * @param eventID The UUID of the event to be deleted.
     * @return ResponseEntity<?> Returns the response entity that includes the status of the operation.
     */
    @DeleteMapping(value = "events/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteEvent(@PathVariable("eventID") UUID eventID) {
        log.info("DELETE localhost:8080/events/{} -> deleteEvent({}) is called", eventID, eventID);

        ResponseEntity<?> response = eventService.delete(eventID);

        return response;
    }

    /**
     * Add a participant to an event.
     *
     * @param eventID The ID of the event to which the user will be added.
     * @param userID  The ID of the user to be added to the event.
     * @return ResponseEntity<?> Returns the response entity that includes the status of the operation.
     */
    @PutMapping(value = "events/{eventID}/add/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addParticipant(@PathVariable("eventID") UUID eventID,
            @PathVariable("userID") UUID userID) {
        log.info("PUT localhost:8080/events/{}/add/{} -> addParticipant({}, {}) is called", eventID, userID, eventID, userID);

        ResponseEntity<?> response;
        if (userService.getUser(userID).getStatusCode() == HttpStatus.NOT_FOUND) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } else {
            response = eventService.addUser(eventID, userID);
        }

        return response;
    }

    /**
     * Delete a user.
     *
     * @param userID The ID of the user to be deleted.
     * @return ResponseEntity<?> Returns the response entity that includes the status of the operation.
     */
    @DeleteMapping(value = "users/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable("userID") UUID userID) {
        log.info("PUT localhost:8080/users/{} -> deleteUser({})", userID, userID);

        eventService.removeUserFromAllEvents(userID);
        ResponseEntity<?> response = userService.delete(userID);

        return response;
    }

    /**
     * Used to get all events.
     *
     * @return ResponseEntity<?> Returns the response entity that includes all events.
     */
    @GetMapping(value = "events", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEvents() {
        log.info("GET localhost:8080/events -> getAllEvents() is called");

        ResponseEntity<?> response = eventService.getAllDTO();

        return response;
    }

    /**
     * Get a specific event by its ID.
     *
     * @param eventID The ID of the event to be retrieved.
     * @return ResponseEntity<?> Returns the response entity that includes the requested event.
     */
    @GetMapping(value = "events/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEvent(@PathVariable("eventID") UUID eventID) {
        log.info("GET localhost:8080/events/{} -> getEvent({}) is called", eventID, eventID);

        ResponseEntity<?> response = eventService.getEvent(eventID);

        return response;
    }

    // TODO javadoc
    @GetMapping(value = "events/{eventID}/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEventParticipants(@PathVariable("eventID") UUID eventID) {
        log.info("GET localhost:8080/events/{}/participants -> getEventParticipants({}) is called", eventID, eventID);

        ResponseEntity<?> eventResponse = eventService.getEvent(eventID);
        if(eventResponse.getStatusCode()==HttpStatus.NOT_FOUND){
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
     * Rating of an event by a user.
     *
     * @param eventID The ID of the event to be rated.
     * @param userID  The ID of the user who rates the event.
     * @param rating  The rating given by the user to the event.
     * @return ResponseEntity<?> Returns the response entity that includes the status of the operation.
     */
    @PutMapping(value = "events/{eventID}/{userID}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> rateEvent(@PathVariable("eventID") UUID eventID, @PathVariable("userID") UUID userID,
            @PathVariable("rating") int rating) {
        log.info("PUT localhost:8080/events/{}/{}/{} -> rateEvent({}, {}, {}) is called", eventID, userID, rating, eventID, userID, rating);

        ResponseEntity<?> response = eventService.addRating(eventID, userID, rating);

        return response;
    }
}