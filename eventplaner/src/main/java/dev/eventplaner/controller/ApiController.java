package dev.eventplaner.controller;

import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.eventplaner.model.EventDTO;
import dev.eventplaner.model.User;
import dev.eventplaner.model.Event;
import dev.eventplaner.model.UserDTO;
import dev.eventplaner.service.EventService;
import dev.eventplaner.service.UserService;


@RestController
@RequestMapping("/api")
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/events",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Collection<EventDTO>> getAllEvents() {
        log.info("Get all events");
        Collection<EventDTO> events = eventService.getAllDTO();

        if (events.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(value = "/users",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Collection<UserDTO>> getAllUsers() {
        log.info("Get all users");
        Collection<UserDTO> users = userService.getAllDTO();

        if (users.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/events/{eventID}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Event> getEvent(@PathVariable("eventID") UUID eventID) {
        log.info("Get all users");
        Event event = eventService.getEvent(eventID);
        
        if (event == null) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userID}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable("userID") UUID userID) {
        log.info("Get all users");
        User user = userService.getUser(userID);
        
        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
