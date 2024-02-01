package dev.repoplaner.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import dev.repoplaner.model.User;
import dev.repoplaner.model.Event;
import dev.repoplaner.service.RepositoryService;

/**
 * This class is the controller for the API endpoints related to events and users.
 * It handles the creation, retrieval, update, and deletion of events and users.
 */
@RestController
public class ApiController {

    /**
     * The logger for the ApiController class.
     */
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private RepositoryService repositoryService;

    /**
     * Creates a new event.
     *
     * @param event the event object to be created
     * @return the ResponseEntity containing the created event
     * @throws URISyntaxException if there is an error in the URI syntax
     */
    @PostMapping(value = "/events")
    public ResponseEntity<?> createEvent(@RequestBody Event event) throws URISyntaxException {
        Event createdEvent = repositoryService.putEvent(event);
        URI url = new URI("/events/" + createdEvent.getID());
        return ResponseEntity.created(url).body(createdEvent);
    }

    /**
     * Updates an existing event with the given event ID.
     *
     * @param eventID The ID of the event to be updated.
     * @param event   The updated event object.
     * @return The ResponseEntity containing the updated event object if successful, or a not found response if the event does not exist.
     */
    @PutMapping("/events/{eventID}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID eventID, @RequestBody Event event) {
        if (repositoryService.getEvent(eventID) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repositoryService.putEvent(event.setID(eventID)));
    }

    /**
        * Retrieves all events from the repository.
        *
        * @return ResponseEntity containing a collection of Event objects.
        *         If no events are found, returns a ResponseEntity with no content.
        */
    @GetMapping("/events")
    public ResponseEntity<Collection<Event>> getAllEvents() {
        Collection<Event> events = repositoryService.getAllEvents();
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }

    /**
     * Retrieves the event with the specified event ID.
     *
     * @param eventID the ID of the event to retrieve
     * @return the ResponseEntity containing the event if found, or a not found response if the event does not exist
     */
    @GetMapping("/events/{eventID}")
    public ResponseEntity<Event> getEvent(@PathVariable UUID eventID) {
        Event event = repositoryService.getEvent(eventID);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    /**
     * Deletes an event with the specified event ID.
     *
     * @param eventID the ID of the event to be deleted
     * @return a ResponseEntity with no content if the event is successfully deleted, or a ResponseEntity with not found status if the event does not exist
     */
    @DeleteMapping("/events/{eventID}")
    public ResponseEntity<Event> deleteEvent(@PathVariable UUID eventID) {
        if (repositoryService.deleteEvent(eventID) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Creates a new user.
     *
     * @param user the user object to be created
     * @return the ResponseEntity containing the created user
     * @throws URISyntaxException if there is an error in the URI syntax
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
        User createdUser = repositoryService.putUser(user);
        URI url = new URI("/users/" + createdUser.getID());
        return ResponseEntity.created(url).body(createdUser);
    }

    /**
        * Updates a user with the given userID.
        *
        * @param userID The ID of the user to be updated.
        * @param user   The updated user object.
        * @return ResponseEntity<User> The response entity containing the updated user.
        */
    @PutMapping("/users/{userID}")
    public ResponseEntity<User> updateUser(@PathVariable UUID userID, @RequestBody User user) {
        if (repositoryService.getUser(userID) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repositoryService.putUser(user.setID(userID)));
    }

    /**
        * Retrieves all users from the repository.
        *
        * @return ResponseEntity containing a collection of User objects if users exist, or a no content response if no users are found.
        */
    @GetMapping("/users")
    public ResponseEntity<Collection<User>> getAllUsers() {
        Collection<User> users = repositoryService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a user with the specified userID.
     *
     * @param userID the unique identifier of the user
     * @return the ResponseEntity containing the user if found, or a not found response if the user does not exist
     */
    @GetMapping("/users/{userID}")
    public ResponseEntity<User> getUsers(@PathVariable UUID userID) {
        User user = repositoryService.getUser(userID);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    /**
     * Deletes a user with the specified userID.
     * 
     * @param userID the UUID of the user to be deleted
     * @return ResponseEntity<User> representing the HTTP response
     */
    @DeleteMapping("/users/{userID}")
    public ResponseEntity<User> deleteUsers(@PathVariable UUID userID) {
        if (repositoryService.deleteUser(userID) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}