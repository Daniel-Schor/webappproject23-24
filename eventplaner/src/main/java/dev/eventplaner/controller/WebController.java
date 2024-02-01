package dev.eventplaner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import dev.eventplaner.model.Event;
import dev.eventplaner.model.Geolocation;
import dev.eventplaner.model.User;
import dev.eventplaner.model.UserDTO;

import java.time.LocalDateTime;
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
    private ApiController apiController;

    /**
     * Displays all events on the webpage.
     *
     * Mapped to the GET request at '/events', this method retrieves all events
     * using the apiController
     * and adds them to the model for rendering. It handles any exceptions during
     * the retrieval process.
     * The events are expected to be in JSON format and are converted to a
     * collection of Event objects.
     *
     * @param model The Model object used to add attributes to the view.
     * @return The name of the view template (e.g., 'events') to render the events.
     */
    @GetMapping("/events")
    public String showAllEvents(Model model) {
        log.info("GET localhost:8080/web/events -> showAllEvents is called");

        try {
            ResponseEntity<?> response = apiController.getAllEvents();
            String jsonResponse = (String) response.getBody(); // Assuming the response body is a JSON string
            Collection<Event> events = Event.collectionFromJson(jsonResponse);
            model.addAttribute("events", events);
        } catch (Exception e) {
            log.error("Error retrieving events", e);
        }

        return "events";
    }

    /**
     * Displays the details of a specific event on the webpage.
     *
     * Mapped to the GET request at 'events/{eventID}', this method retrieves the
     * details of an event
     * identified by eventID using the apiController. It adds the event details to
     * the model if the retrieval
     * is successful. It also handles cases where the event is not found or the
     * response is of an unexpected type.
     *
     * @param eventID The UUID of the event whose details are to be displayed.
     * @param model   The Model object used to add attributes to the view.
     * @return The name of the view template (e.g., 'event-details') to render the
     *         event details.
     */
    @GetMapping("events/{eventID}")
    public String showEventDetails(@PathVariable("eventID") UUID eventID, Model model) {
        log.info("GET localhost:8080/web/events/{} -> showEventDetails() is called: {}", eventID, eventID);

        ResponseEntity<?> response = apiController.getEvent(eventID);

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
     * Displays the details of an event identified by its ID on the webpage.
     *
     * This method is mapped to the GET request at '/web/event-details/{id}'. It
     * retrieves the details
     * of an event identified by the provided UUID 'id' using the apiController. The
     * event details are then added to the model for rendering on the view.
     *
     * @param id    The UUID of the event whose details are to be displayed.
     * @param model The Model object used to pass attributes to the view.
     * @return The name of the view template (e.g., 'event-details') used to render
     *         the event details.
     */
    // TODO mapping ohne /web
    @GetMapping("event-details/{id}")
    public String showEventDetailsById(@PathVariable("id") UUID id, Model model) {
        log.info("GET localhost:8080/web/event-details/{} -> showEventDetailsById() is called: {}", id, id);

        ResponseEntity<?> event = apiController.getEvent(id);
        model.addAttribute("event", event);

        return "event-details";
    }

    /**
     * Displays all users on the webpage.
     *
     * Mapped to the GET request at 'users', this method retrieves all users using
     * the apiController
     * and adds them to the model for rendering on the view. The method handles
     * exceptions that may occur
     * during the retrieval process. Users are expected to be in JSON format and are
     * converted to a collection of UserDTO objects.
     *
     * @param model The Model object used to add attributes to the view.
     * @return The name of the view template (e.g., 'users') used to render the user
     *         list.
     */
    @GetMapping("users")
    public String showAllUsers(Model model) {
        log.info("GET localhost:8080/web/users -> showAllUsers() is called");

        try {
            ResponseEntity<?> response = apiController.getAllUsers();
            String jsonResponse = response.getBody().toString(); // Assuming the response body is a JSON string

            Collection<UserDTO> users = UserDTO.collectionFromJsonUserDTO(jsonResponse);

            model.addAttribute("users", users);
        } catch (Exception e) {
            log.error("Error retrieving events", e);
        }

        return "users";
    }

    /**
     * Displays the home page.
     *
     * Mapped to the GET request at 'home', this method returns the view template
     * for the home page.
     * It is responsible for rendering the main landing page of the website.
     *
     * @return The name of the view template used for the home page.
     */
    @GetMapping("home")
    public String home() {
        log.info("GET localhost:8080/web/home -> home() is called");

        return "index";
    }

    /**
     * Displays the event management page.
     *
     * Mapped to the GET request at 'manage', this method is responsible for
     * rendering the event management
     * view. It simply returns the name of the view template responsible for
     * displaying the event management interface.
     *
     * @param model The Model object used to pass attributes to the view. It may not
     *              be used explicitly in this method.
     * @return The name of the view template (e.g., 'manage-events') used to render
     *         the event management page.
     */
    @GetMapping("manage")
    public String showManageEvents(Model model) {
        log.info("GET localhost:8080/web/manage -> showManageEvents() is called");

        return "manage-events";
    }

    /**
     * Displays the form for adding a new event.
     *
     * This method is mapped to the GET request at 'manage/add-event'. It
     * preparesthe model
     * with a new Event object to be used in a form for adding an event. The form is
     * rendered using the 'add-event' view template.
     *
     * @param model The Model object used to pass attributes to the view.
     * @return The name of the view template (e.g., 'add-event') used for rendering
     *         the form to add a new event.
     */
    @GetMapping("manage/add-event")
    public String showAddEventForm(Model model) {
        log.info("GET localhost:8080/web/manage/add-event -> showAddEventForm() is called");

        model.addAttribute("event", new Event());

        return "add-event";
    }

    /**
     * Displays the page for deleting an event.
     *
     * This method, mapped to the GET request at '/web/manage/delete-event', is
     * responsible for showing
     * the delete event page. The method prepares the view for deletion operations,
     * but does not populate
     * the model with data as the deletion process is typically handled
     * interactively on the page.
     *
     * @param model The Model object used to pass attributes to the view.
     * @return The name of the view template (e.g., 'delete-event') used for
     *         rendering the event deletion page.
     */
    @GetMapping("/web/manage/delete-event")
    public String showDeleteEventPage(Model model) {
        log.info("GET localhost:8080/web/manage/delete-event -> showDeleteEventPage() is called");

        return "delete-event";

    }

    /**
     * Deletes an event identified by its UUID and redirects to an appropriate view.
     *
     * Mapped to the DELETE request at '/manage/delete-event/{eventID}', this method
     * handles
     * the deletion of an event. It calls the apiController to perform the deletion.
     * Depending on the outcome, it redirects to either the management page or back
     * to the
     * events page.
     *
     * @param eventID The UUID of the event to be deleted.
     * @param model   The Model object, not utilized in this method but required for
     *                the API.
     * @return A redirect string to either the management page or the events page
     *         based on deletion success.
     */
    @DeleteMapping("/manage/delete-event/{eventID}")
    public String deleteEvent(@PathVariable("eventID") UUID eventID, Model model) {
        log.info("DELETE localhost:8080/web/manage/delete-event/{} -> deleteEvent() is called: {}", eventID, eventID);

        ResponseEntity<?> response = apiController.deleteEvent(eventID);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("WebController: Event ID: {} deleted", eventID);
            return "redirect:/web/manage";
        } else {
            log.warn("WebController: Error deleting event ID: {}. Status code: {}", eventID, response.getStatusCode());
            return "redirect:/web/events";
        }
    }

    /**
     * Checks the existence of an event by its UUID and returns a JSON response.
     *
     * This method, mapped to the GET request at '/manage/check-event/{eventID}',
     * queries the existence of an event identified by eventID. It uses the
     * apiController to fetch the event. The response indicates
     * whether the event exists with a JSON object containing a boolean 'exists'
     * key.
     *
     * @param eventID The UUID of the event to check for existence.
     * @return ResponseEntity with a JSON object indicating whether the event exists
     *         ({"exists": true} or {"exists": false}).
     */
    @GetMapping("/manage/check-event/{eventID}")
    public ResponseEntity<?> checkEvent(@PathVariable("eventID") UUID eventID) {
        log.info("GET localhost:8080/web/manage/check-event/{} -> checkEvent() is called: {}", eventID, eventID);

        ResponseEntity<?> response = apiController.getEvent(eventID);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("{\"exists\": true}");
        } else {
            return ResponseEntity.ok("{\"exists\": false}");
        }
    }

    /**
     * Adds a new event based on the provided form parameters and redirects to the
     * event overview.
     *
     * Mapped to the POST request at 'manage/add-event', this method handles the
     * creation of a new event using the parameters provided through the form.
     * It constructs a new Event object, sets its properties based on the form
     * inputs,
     * and adds it to the event list using the apiController.
     * After adding the event, it redirects to the event overview page.
     *
     * @param name            The name of the event.
     * @param description     The description of the event.
     * @param dateTime        The date and time of the event.
     * @param latitude        The latitude of the event's location.
     * @param longitude       The longitude of the event's location.
     * @param maxParticipants The maximum number of participants for the event.
     * @param organizerUserID The UUID of the event organizer (optional).
     * @param model           The Model object used to pass attributes to the view.
     * @return A redirect string to the event overview page.
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
        apiController.createEvent(event);

        // Redirect the user to the event overview
        model.addAttribute("events", apiController.getAllEvents());

        // Redirect the user to the event overview
        return "redirect:/web/events";
    }

    @GetMapping("/user-details/{userID}")
    public String showUserDetails(@PathVariable("userID") UUID userID, Model model) {
        log.info("GET localhost:8080/web/user-details/{} -> showUserDetails() is called: {}", userID, userID);

        ResponseEntity<?> userResponse = apiController.getUser(userID);

        if (userResponse.getStatusCode() == HttpStatus.OK) {
            User user = User.userFromJson(userResponse.getBody().toString());
            model.addAttribute("user", user);
        } else {
            log.warn("WebController: Error retrieving user ID: {}. Status code: {}", userID,
                    userResponse.getStatusCode());
        }

        return "user-details";
    }

}