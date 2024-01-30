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

@RestController
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private RepositoryService repositoryService;

    // TODO add validation
    @PostMapping(value = "/events")
    public ResponseEntity<?> createEvent(@RequestBody Event event) throws URISyntaxException {
        Event createdEvent = repositoryService.putEvent(event);
        URI url = new URI("/events/" + createdEvent.getID());
        return ResponseEntity.created(url).body(createdEvent);
    }

    @PutMapping("/events/{eventID}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID eventID, @RequestBody Event event) {
        if (repositoryService.getEvent(eventID) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repositoryService.putEvent(event.setID(eventID)));
    }

    @GetMapping("/events")
    public ResponseEntity<Collection<Event>> getAllEvents() {
        Collection<Event> events = repositoryService.getAllEvents();
        if (events.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{eventID}")
    public ResponseEntity<Event> getEvent(@PathVariable UUID eventID) {
        Event event = repositoryService.getEvent(eventID);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/events/{eventID}")
    public ResponseEntity<Event> deleteEvent(@PathVariable UUID eventID) {
        if (repositoryService.deleteEvent(eventID) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // TODO add validation
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
        User createdUser = repositoryService.putUser(user);
        URI url = new URI("/users/" + createdUser.getID());
        return ResponseEntity.created(url).body(createdUser);
    }

    @PutMapping("/users/{userID}")
    public ResponseEntity<User> updateUser(@PathVariable UUID userID, @RequestBody User user) {
        if (repositoryService.getUser(userID) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(repositoryService.putUser(user.setID(userID)));
    }

    @GetMapping("/users")
    public ResponseEntity<Collection<User>> getAllUsers() {
        Collection<User> users = repositoryService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userID}")
    public ResponseEntity<User> getUsers(@PathVariable UUID userID) {
        User user = repositoryService.getUser(userID);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{userID}")
    public ResponseEntity<User> deleteUsers(@PathVariable UUID userID) {
        if (repositoryService.deleteUser(userID) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}