package dev.repoplaner.controller;

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

    @PostMapping(value = "/events")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(repositoryService.putEvent(event));
    }

    @PutMapping("/events/{eventID}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID eventID, @RequestBody Event event) {
        if (repositoryService.getEvent(eventID)==null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repositoryService.putEvent(event.setID(eventID)));
    }

    @GetMapping("/events")
    public ResponseEntity<Collection<Event>> getAllEvents() {
        return ResponseEntity.ok(repositoryService.getAllEvents());
    }

    @GetMapping("/events/{eventID}")
    public ResponseEntity<Event> getEvent(@PathVariable UUID eventID) {
        return ResponseEntity.ok(repositoryService.getEvent(eventID));
    }


    @DeleteMapping("/events/{eventID}")
    public ResponseEntity<Event> deleteEvent(@PathVariable UUID eventID) {
        return ResponseEntity.ok(repositoryService.deleteEvent(eventID));
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(repositoryService.putUser(user));
    }

    @PutMapping("/users/{userID}")
    public ResponseEntity<User> updateUser(@PathVariable UUID userID, @RequestBody User user) {
        if (repositoryService.getUser(userID)==null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repositoryService.putUser(user.setID(userID)));
    }

    @GetMapping("/users")
    public ResponseEntity<Collection<User>> getAllUsers() {
        return ResponseEntity.ok(repositoryService.getAllUsers());
    }

    @GetMapping("/users/{userID}")
    public ResponseEntity<User> getUsers(@PathVariable UUID userID) {
        return ResponseEntity.ok(repositoryService.getUser(userID));
    }

    @DeleteMapping("/users/{userID}")
    public ResponseEntity<User> deleteUsers(@PathVariable UUID userID) {
        return ResponseEntity.ok(repositoryService.deleteUser(userID));
    }
}