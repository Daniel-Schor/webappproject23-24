package dev.eventplaner.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.eventplaner.model.Event;
import dev.eventplaner.model.User;
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
    public ResponseEntity<Collection<Event>> getAllEvents() {
        log.info("Get all events");
        Collection<Event> events = eventService.getAll();

        if (events.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(value = "/user",
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Collection<User>> getAllUser() {
        log.info("Get all user");
        Collection<User> users = userService.getAll();

        if (users.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
