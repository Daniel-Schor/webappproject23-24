package dev.eventplaner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dev.eventplaner.model.Event;
import dev.eventplaner.model.UserDTO;
import dev.eventplaner.service.EventService;
import dev.eventplaner.service.UserService;

import java.util.Collection;
import java.util.UUID;


@Controller
public class WebController {

    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("/web/events")
    public String showAllEvents(Model model) {
        log.info("WebController: Showing all events");
        model.addAttribute("events", eventService.getAllDTO());
        return "events";
    }

    @GetMapping("/web/events/{eventID}")
    public String showEventDetails(@PathVariable("eventID") UUID eventID, Model model) {
        log.info("WebController: Showing details for event ID: {}", eventID);
        try {
            Event event = eventService.getEvent(eventID);
            model.addAttribute("event", event);
        } catch (IllegalArgumentException e) {
            log.warn("WebController: Invalid event ID format: {}", eventID);
        }
        return "event-details";
    }

    @GetMapping("/web/users")
    public String showAllUsers(Model model) {
        log.info("WebController: Showing all users");
        Collection<UserDTO> usersDTO = userService.getAllDTO();
        if (usersDTO != null) {
            model.addAttribute("users", userService.getAllDTO());
        } else {
            // Handle case when usersDTO is null
            log.warn("WebController: UsersDTO is null");
        }
        return "users";
    }
    
    @GetMapping("/web/home")
    public String home() {
        log.info("WebController: Home page requested");
        return "index";
    }
}
