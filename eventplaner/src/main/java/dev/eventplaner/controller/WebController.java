package dev.eventplaner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import dev.eventplaner.model.Event;
import dev.eventplaner.model.Geolocation;
import dev.eventplaner.model.UserDTO;
import dev.eventplaner.service.EventService;
import dev.eventplaner.service.UserService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/web/")
public SomeData requestMethodName(@RequestParam String param) {
    return new SomeData();
}

public class WebController {

    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("events")
    public String showAllEvents(Model model) {
        log.info("WebController: Showing all events");
        model.addAttribute("events", eventService.getAllDTO());
        return "events";
    }

    @GetMapping("events/{eventID}")
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

    @GetMapping("users")
    public String getUsers(Model model) {
        Collection<UserDTO> users = userService.getAllDTO();
        model.addAttribute("users", users);
        log.info("Fetched all users"); 
        return "users";
    }

    @GetMapping("home")
    public String home() {
        log.info("WebController: Home page requested");
        return "index";
    }
    @GetMapping("manage")
    public String showManageEvents(Model model) {
        log.info("WebController: Showing manage events page");
        return "manage-events";
    }

    @GetMapping("manage/add-event")
    public String showAddEventForm(Model model) {
        log.info("WebController: Showing add event form");
        model.addAttribute("event", new Event());
        return "add-event";
    }
    @PostMapping("manage/add-event")
    public String addEvent(@RequestParam("name") String name,
                           @RequestParam("description") String description,
                           @RequestParam("dateTime") LocalDateTime dateTime,
                           @RequestParam("latitude") Double latitude,
                           @RequestParam("longitude") Double longitude,
                           @RequestParam("maxParticipants") int maxParticipants,
                           @RequestParam(value = "organizerUserID", required = false) UUID organizerUserID,
                           Model model) {
        log.info("WebController: Adding new event: {}", name);

        // Mache neue Event Instanz
        Event event = new Event();

        // Stecke die Parameter in die Event Instanz
        event.setName(name);
        event.setDescription(description);
        event.setDateTime(dateTime);

        // Erstelle eine Geolocation Instanz und stecke sie in die Event Instanz
        Geolocation geolocation = new Geolocation(latitude, longitude);
        event.setLocation(geolocation);

        event.setMaxParticipants(maxParticipants);
        event.setOrganizerUserID(organizerUserID);

        // Füge das Event der Eventliste hinzu
        eventService.create(event);

        // Redirect the user to the event overview
        model.addAttribute("events", eventService.getAllDTO());

        // Redirect the user to the event overview
        return "redirect:events";
    }
    
}
