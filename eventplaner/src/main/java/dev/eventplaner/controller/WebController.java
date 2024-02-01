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

    // TODO javadoc
    @GetMapping("/events")
    public String showAllEvents(Model model) {
        log.info("GET localhost:8080/web/events -> showAllEvents is called");

        try {
            ResponseEntity<?> response = eventService.getAllDTO();
            String jsonResponse = (String) response.getBody(); // Assuming the response body is a JSON string
            Collection<Event> events = collectionFromJson(jsonResponse);
            model.addAttribute("events", events);
        } catch (Exception e) {
            log.error("Error retrieving events", e);
        }

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

    // TODO javadoc
    public static Collection<Event> collectionFromJson(String s) {
        log.info("collectionFromJson() is called");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Collection<Event> values = new ArrayList<>();

        try {
            values = mapper.readValue(s, new TypeReference<Collection<Event>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return values;
    }

    // TODO javadoc
    @GetMapping("events/{eventID}")
    public String showEventDetails(@PathVariable("eventID") UUID eventID, Model model) {
        log.info("GET localhost:8080/web/events/{eventID} -> showEventDetails() is called: {}", eventID);

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

    // TODO javadoc
    // TODO mapping ohne /web
    @GetMapping("/web/event-details/{id}")
    public String showEventDetailsById(@PathVariable("id") UUID id, Model model) {
        log.info("GET localhost:8080/web/event-details/{id} -> showEventDetailsById() is called: {}", id);

        ResponseEntity<?> event = eventService.getEvent(id);
        model.addAttribute("event", event);

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
        log.info("GET localhost:8080/web/users -> showAllUsers() is called");

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
        log.info("GET localhost:8080/web/home -> home() is called");

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
        log.info("GET localhost:8080/web/manage -> showManageEvents() is called");

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
        log.info("GET localhost:8080/web/manage/add-event -> showAddEventForm() is called");

        model.addAttribute("event", new Event());

        return "add-event";
    }

    // TODO javadoc
    @GetMapping("/web/manage/delete-event")
    public String showDeleteEventPage(Model model) {
        log.info("GET localhost:8080/web/manage/delete-event -> showDeleteEventPage() is called");

        return "delete-event";

    }

    // TODO javadoc
    @DeleteMapping("/manage/delete-event/{eventID}")
    public String deleteEvent(@PathVariable("eventID") UUID eventID, Model model) {
        log.info("DELETE localhost:8080/web/manage/delete-event/{eventID} -> deleteEvent() is called: {}", eventID);

        ResponseEntity<?> response = eventService.delete(eventID);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("WebController: Event ID: {} deleted", eventID);
            return "redirect:/web/manage";
        } else {
            log.warn("WebController: Error deleting event ID: {}. Status code: {}", eventID, response.getStatusCode());
            return "redirect:/web/events";
        }
    }

    // TODO javadoc
    @GetMapping("/manage/check-event/{eventID}")
    public ResponseEntity<?> checkEvent(@PathVariable("eventID") UUID eventID) {
        log.info("GET localhost:8080/web/manage/check-event/{eventID} -> checkEvent() is called: {}", eventID);


        ResponseEntity<?> response = eventService.getEvent(eventID);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("{\"exists\": true}");
        } else {
            return ResponseEntity.ok("{\"exists\": false}");
        }
    }

    // TODO javadoc
    // TODO add particapnts spalte löschen
    @PostMapping("manage/add-event")
    public String addEvent(@RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("dateTime") LocalDateTime dateTime,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("maxParticipants") int maxParticipants,
            @RequestParam(value = "organizerUserID", required = false) UUID organizerUserID,
            Model model) {

        // Create new event instance
        Event event = new Event();

        log.info("POST localhost:8080/web/manage/add-event -> addEvent() is called: {}", event.getID());
        
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

    @GetMapping("/user-details/{userID}")
    public String showUserDetails(@PathVariable("userID") UUID userID, Model model) {
        log.info("GET localhost:8080/web/user-details/{userID} -> showUserDetails() is called: {}", userID);

        ResponseEntity<?> userResponse = userService.getUser(userID);

        if (userResponse.getStatusCode() == HttpStatus.OK) {
            Object responseBody = userResponse.getBody();

            if (responseBody instanceof UserDTO) {
                UserDTO user = (UserDTO) responseBody;
                model.addAttribute("user", user);
            } else {
                log.warn("WebController: Invalid response body type for user ID: {}", userID);
            }
        } else {
            log.warn("WebController: Error retrieving user ID: {}. Status code: {}", userID,
                    userResponse.getStatusCode());
        }

        return "user-details";
    }

}