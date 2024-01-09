package dev.eventplaner.controller;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.eventplaner.model.User;
import dev.eventplaner.model.Event;
import dev.eventplaner.service.RepositoryService;

@RestController
public class ApiController {

    @Autowired
    private RepositoryService repositoryService;

    @PostMapping("/event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(repositoryService.putEvent(event));
    }

    @GetMapping("/event")
    public ResponseEntity<Collection<Event>> getAllEvents() {
        return ResponseEntity.ok(repositoryService.getAllEvents());
    }

    @GetMapping("/event/{eventID}")
    public ResponseEntity<Event> getEvent(@PathVariable UUID eventID) {
        return ResponseEntity.ok(repositoryService.getEvent(eventID));
    }

    @DeleteMapping("/event/{eventID}")
    public ResponseEntity<Event> deleteEvent(@PathVariable UUID eventID) {
        return ResponseEntity.ok(repositoryService.deleteEvent(eventID));
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(repositoryService.putUser(user));
    }

    @GetMapping("/user")
    public ResponseEntity<Collection<User>> getAllUsers() {
        return ResponseEntity.ok(repositoryService.getAllUsers());
    }

    @GetMapping("/Users/{id}")
    public ResponseEntity<User> getUsers(@PathVariable UUID userID) {
        return ResponseEntity.ok(repositoryService.getUser(userID));
    }

    @DeleteMapping("/Users/{userID}")
    public ResponseEntity<User> deleteUsers(@PathVariable UUID userID) {
        return ResponseEntity.ok(repositoryService.deleteUser(userID));
    }
}