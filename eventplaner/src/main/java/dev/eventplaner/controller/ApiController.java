package dev.eventplaner.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.eventplaner.model.EventDTO;
import dev.eventplaner.model.User;
import dev.eventplaner.model.Event;
import dev.eventplaner.model.UserDTO;
import dev.eventplaner.service.EventService;
import dev.eventplaner.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class ApiController {

    // Logger instance for this class, used to log system messages, warnings, and
    // errors.
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    // Autowired annotation is used to automatically inject the EventService and
    // UserService
    // instance into this class.
    @Autowired
    private EventService eventService;
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
        User updatedUser = userService.updateUser(userID, user);

        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
    }

    /**
     * Deletes a user with the specified userID.
     *
     * @param userID The ID of the user to be deleted.
     * @return A ResponseEntity containing the deleted user if successful, or a not
     *         found response if the user does not exist.
     */
    @DeleteMapping(value = "/users/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable("userID") UUID userID) {
        log.debug("deleteUser() is called");
        User user = userService.delete(userID);
        eventService.removeUser(userID);
        if (userID == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Retrieves all events.
     *
     * @return ResponseEntity containing a collection of EventDTO objects
     */
    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Collection<EventDTO>> getAllEvents() {
        log.info("Get all events");
        Collection<EventDTO> events = eventService.getAllDTO();

        if (events.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Retrieves the event with the specified event ID.
     *
     * @param eventID the ID of the event to retrieve
     * @return a ResponseEntity containing the event if found, or no content if not
     *         found
     */
    @GetMapping(value = "/events/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Event> getEvent(@PathVariable("eventID") UUID eventID) {
        log.info("Get all users");
        Event event = eventService.getEvent(eventID);

        if (event == null) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    // neue Methode

    /**
     * Retrieves the participants of an event.
     *
     * @param eventID The ID of the event.
     * @return A ResponseEntity containing a collection of UserDTO objects
     *         representing the participants of the event.
     */
    @GetMapping(value = "/events/{eventID}/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Collection<UserDTO>> getEventParticipants(@PathVariable("eventID") UUID eventID) {
        log.info("Get all users");
        Collection<UUID> usersUUID = eventService.getEvent(eventID).getParticipants().keySet();
        Collection<UserDTO> users = new ArrayList<>();

        for (UUID uuid : usersUUID) {
            users.add(new UserDTO(userService.getUser(uuid)));
        }

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Creates a new event.
     *
     * @param event The event object containing the details of the event to be
     *              created.
     * @return ResponseEntity representing the HTTP response with the created event
     *         or an error message.
     */
    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        log.info("Create new Event: {}", event.getName());
        if (event.getName() == null || event.getName().isEmpty()) {
            String detail = "Event name must not be null or empty";
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
            pd.setInstance(URI.create("/events"));
            pd.setTitle("Event creation error");
            return ResponseEntity.unprocessableEntity().body(pd);
        }
        Event createdEvent = eventService.create(event);
        return new ResponseEntity<Event>(createdEvent, HttpStatus.CREATED);
    }

    // neue Methode

    /**
     * Updates an event with the given event ID.
     *
     * @param eventID The ID of the event to be updated.
     * @param event   The updated event object.
     * @return ResponseEntity containing the updated event if successful, or
     *         ResponseEntity with status 404 if the event is not found.
     */
    @PutMapping(value = "/events/{eventID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Event> updateEvent(@PathVariable("eventID") UUID eventID, @RequestBody Event event) {
        log.info("Update event: {}", eventID);
        Event updatedEvent = eventService.update(eventID, event);

        if (updatedEvent == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Event>(updatedEvent, HttpStatus.OK);
    }

    /**
     * Deletes an event with the specified event ID.
     *
     * @param eventID The ID of the event to be deleted.
     * @return A ResponseEntity containing the deleted event if successful, or a not
     *         found response if the event does not exist.
     */
    @DeleteMapping(value = "/events/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteEvent(@PathVariable("eventID") UUID eventID) {
        log.debug("deleteEvent() is called");
        Event event = eventService.delete(eventID);
        if (eventID == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    // neue Methode

    /**
     * Adds a participant to an event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user to be added as a participant.
     * @return The ResponseEntity containing the updated event information.
     */
    @PutMapping(value = "/events/{eventID}/add/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addParticipant(@PathVariable("eventID") UUID eventID, @PathVariable("userID") UUID userID) {
        log.debug("addParticipant() is called");
        Event event = eventService.addUser(eventID, userID);
        if (eventID == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    // neue Methode

    /**
     * Removes a participant from an event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user to be removed.
     * @return A ResponseEntity containing the updated event if successful, or a not
     *         found response if the event ID is invalid.
     */
    @PutMapping(value = "/events/{eventID}/remove/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> removeParticipant(@PathVariable("eventID") UUID eventID, @PathVariable("userID") UUID userID) {
        log.debug("removeParticipant() is called");
        Event event = eventService.removeUser(eventID, userID);
        if (eventID == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    // neue Methode
    /**
     * Updates the rating of an event by a user.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user.
     * @param rating  The new rating for the event.
     * @return ResponseEntity containing the updated Event object if successful, or
     *         a not found response if the event ID is invalid.
     */
    @PutMapping(value = "/events/{eventID}/{userID}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> rateEvent(@PathVariable("eventID") UUID eventID, @PathVariable("userID") UUID userID, @PathVariable("rating") int rating) {
        log.debug("rateEvent() is called");
        Event event = eventService.addRating(eventID, userID, rating);
        if (eventID == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

}
