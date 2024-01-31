package dev.eventplaner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dev.eventplaner.model.Event;
import dev.eventplaner.model.Geolocation;
import dev.eventplaner.model.UserDTO;
import dev.eventplaner.service.EventService;
import dev.eventplaner.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Controller
@RequestMapping("/web/")
/*
 * public SomeData requestMethodName(@RequestParam("param") String param) {
 * return new SomeData();
 * }
 */

public class WebController {

    private static final Logger log = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    /**
     * Show all events.
     *
     * @param model The Model object to be populated with event data.
     * @return String Returns the name of the view to be rendered.
     */
    @GetMapping("/events")
    public String showAllEvents(Model model) {
        log.info("WebController: Showing all events");

        try {
            ResponseEntity<?> response = eventService.getAllDTO();
            String jsonResponse = (String) response.getBody(); // Assuming the response body is a JSON string
            Collection<Event> events = collectionFromJson(jsonResponse);
            model.addAttribute("events", events);
        } catch (Exception e) {
            log.error("Error retrieving events", e);
        }

        return "events";
    }

    /**
     * Convert a JSON string to a collection of Event objects.
     *
     * @param s The JSON string to be converted.
     * @return Collection<Event> Returns the collection of Event objects.
     */
    public static Collection<Event> collectionFromJson(String s) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Collection<Event> values = new ArrayList<>();

        try {
            values = mapper.readValue(s, new TypeReference<Collection<Event>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * Show the details of a specific event.
     *
     * @param eventID The ID of the event to be shown.
     * @param model   The Model object to be populated with event data.
     * @return String Returns the name of the view to be rendered.
     */
    @GetMapping("events/{eventID}")
    public String showEventDetails(@PathVariable("eventID") UUID eventID, Model model) {
        log.info("WebController: Showing details for event ID: {}", eventID);
        ResponseEntity<?> response = eventService.getEvent(eventID);

        if (response.getStatusCode() == HttpStatus.OK) {
            Object responseBody = response.getBody();

            if (responseBody instanceof Event) {
                Event event = (Event) responseBody;
                model.addAttribute("event", event);
            } else {
                log.warn("WebController: Invalid response body type for event ID: {}", eventID);
            }
        } else {
            log.warn("WebController: Error retrieving event ID: {}. Status code: {}", eventID,
                    response.getStatusCode());
        }

        return "event-details";
    }

    /**
     * Show all users.
     *
     * @param model The Model object to be populated with user data.
     * @return String Returns the name of the view to be rendered.
     */
    @GetMapping("users")
    public String showAllUsers(Model model) {
        try {
            ResponseEntity<?> response = userService.getAllDTO();
            String jsonResponse = response.getBody().toString(); // Assuming the response body is a JSON string

            Collection<UserDTO> users = UserDTO.collectionFromJsonUserDTO(jsonResponse);

            model.addAttribute("users", users);
        } catch (Exception e) {
            log.error("Error retrieving events", e);
        }
        return "users";
    }

    /**
     * Show the home page.
     *
     * @return String Returns the name of the view to be rendered.
     */
    @GetMapping("home")
    public String home() {
        log.info("WebController: Home page requested");
        return "index";
    }

    /**
     * Show the manage events page.
     *
     * @param model The Model object to be populated with event data.
     * @return String Returns the name of the view to be rendered.
     */
    @GetMapping("manage")
    public String showManageEvents(Model model) {
        log.info("WebController: Showing manage events page");
        return "manage-events";
    }

    /**
     * Show the add event form.
     *
     * @param model The Model object to be populated with event data.
     * @return String Returns the name of the view to be rendered.
     */
    @GetMapping("manage/add-event")
    public String showAddEventForm(Model model) {
        log.info("WebController: Showing add event form");
        model.addAttribute("event", new Event());
        return "add-event";
    }

    /**
     * Show the delete event page.
     *
     * @param eventID The ID of the event to be deleted.
     * @param model   The Model object to be populated with event data.
     * @return String Returns the name of the view to be rendered.
     */
    @GetMapping("manage/delete-event")
    public String showDeleteEventPage(@PathVariable("eventID") UUID eventID, Model model) {
        // Add any necessary data to the model when needed
        return "delete-event";
    }

    /**
     * Delete an event.
     *
     * @param eventID The ID of the event to be deleted.
     * @param model   The Model object to be populated with event data.
     * @return String Returns the name of the view to be rendered.
     */
    @DeleteMapping("manage/delete-event")
    public String deleteEvent(@PathVariable("eventID") UUID eventID, Model model) {
        log.info("WebController: Deleting event ID: {}", eventID);
        ResponseEntity<?> response = eventService.delete(eventID);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("WebController: Event ID: {} deleted", eventID);
        } else {
            log.warn("WebController: Error deleting event ID: {}. Status code: {}", eventID, response.getStatusCode());
        }

        // Here you can decide where you want to redirect to after deletion
        return "redirect:/web/manage"; // Example: Redirect to the Manage page
    }

    /**
     * Add a new event.
     *
     * @param name            The name of the event.
     * @param description     The description of the event.
     * @param dateTime        The date and time of the event.
     * @param latitude        The latitude of the event's location.
     * @param longitude       The longitude of the event's location.
     * @param maxParticipants The maximum number of participants for the event.
     * @param organizerUserID The UUID of the user who is organizing the event.
     * @param model           The Model object to be populated with event data.
     * @return String Returns the name of the view to be rendered.
     */
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

        // Create new event instance
        Event event = new Event();

        // Insert the parameters into the event instance
        event.setName(name);
        event.setDescription(description);
        event.setDateTime(dateTime);

        // Create a geolocation instance and insert it into the event instance
        Geolocation geolocation = new Geolocation(latitude, longitude);
        event.setLocation(geolocation);

        event.setMaxParticipants(maxParticipants);
        event.setOrganizerUserID(organizerUserID);

        // Add the event to the event list
        eventService.create(event);

        // Redirect the user to the event overview
        model.addAttribute("events", eventService.getAllDTO());

        // Redirect the user to the event overview
        return "redirect:events";
    }

}