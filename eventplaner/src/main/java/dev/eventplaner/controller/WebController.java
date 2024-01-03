package dev.eventplaner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dev.eventplaner.model.Event;
import dev.eventplaner.service.EventService;
import java.util.UUID;

@Controller
public class WebController {

    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private EventService eventService;

    @GetMapping("/web/events")
    public String showAllEvents(Model model) {
        log.info("WebController: Showing all events");
        model.addAttribute("events", eventService.getAllDTO());
        return "events";
    }

    @GetMapping("/web/events/{eventID}")
    public String showEventDetails(@PathVariable("eventID") String eventID, Model model) {
        log.info("WebController: Showing details for event ID: {}", eventID);
        try {
            Event event = eventService.getEvent(UUID.fromString(eventID));
            model.addAttribute("event", event);
        } catch (IllegalArgumentException e) {
            log.warn("WebController: Invalid event ID format: {}", eventID);
        }
        return "eventDetails";
    }

    @GetMapping("/web/home")
    public String home() {
        log.info("WebController: Home page requested");
        return "index";
    }
}
