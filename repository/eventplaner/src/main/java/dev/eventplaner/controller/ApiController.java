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

}
