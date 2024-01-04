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

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Collection<UserDTO>> getAllUsers() {
        log.info("Get all users");
        Collection<UserDTO> users = userService.getAllDTO();

        if (users.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

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

    // neu
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

    // neu

    @GetMapping(value = "/events/{eventID}/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Collection<UserDTO>> getEventParticipants(@PathVariable("eventID") UUID eventID) {
        log.info("Get all users");
        Collection<UUID> usersUUID = eventService.getEvent(eventID).getParticipants();
        Collection<UserDTO> users = new ArrayList();

        for (UUID uuid : usersUUID) {
            users.add(new UserDTO(userService.getUser(uuid)));
        }

        if (users.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        log.info("Create new Event: ", event.getName());
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

    // neu
    @PutMapping(value = "/events/{eventID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Event> updateEvent(@PathVariable("eventID") UUID eventID, @RequestBody Event event) {
        log.info("Update event: {}", eventID);
        User updatedEvent = eventService.updateEvent(eventID, event);

        if (updatedEvent == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Event>(updatedEvent, HttpStatus.OK);
    }

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

}
