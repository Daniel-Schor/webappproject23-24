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
     * Mapped to the GET request at '/web/event-details/{id}'. It
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
     * This method handles a GET request to display the home page of the web
     * application.
     * It returns the name of the view template used for rendering the home page.
     *
     * @return The name of the view template for the home page.
     */
    @GetMapping("home")
    public String home() {
        log.info("GET localhost:8080/web/home -> home() is called");

        return "index";
    }

    /**
     * Displays the event management page.
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
     * Handles GET requests for "/view-users" endpoint.
     *
     * @param model The Model object for passing data to the view.
     * @return The view name ("manage-users") for rendering the user management
     *         page.
     */
    @GetMapping("view-users")
    public String showManageUsers(Model model) {
        log.info("GET localhost:8080/web/view-users -> showManageUsres() is called");

        return "manage-users";
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
     * @param model The Model object used to pass attributes to the view.
     * @return The name of the view template (e.g., 'delete-event') used for
     *         rendering the event deletion page.
     */
    @GetMapping("manage/delete-event")
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
    @DeleteMapping("manage/delete-event/{eventID}")
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
    @GetMapping("manage/check-event/{eventID}")
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

    /**
     * Handles GET requests for the "user-details/{userID}" endpoint.
     *
     * This method is responsible for displaying the details of a specific user
     * identified by
     * their userID. It fetches user data using an API call, processes the response,
     * and then
     * populates the model with the user's information if the retrieval is
     * successful. In case
     * of an error in fetching user data, a warning is logged.
     *
     * @param userID The unique identifier (UUID) of the user whose details are to
     *               be displayed.
     * @param model  The Model object used to pass data to the view.
     * @return The name of the view ("user-details") to render the user's details.
     */
    @GetMapping("user-details/{userID}")
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

    /**
     * Handles GET requests for the "manage/delete-user" endpoint.
     *
     * @param model The Model object for passing data to the view.
     * @return The view name ("delete-user") for rendering the delete user page.
     */
    @GetMapping("manage/delete-user")
    public String showDeleteUserPage(Model model) {
        log.info("GET localhost:8080/web/manage/delete-user -> showDeleteUserPage() is called");

        return "delete-user";

    }

    /**
     * Handles GET requests for the "manage/add-user" endpoint.
     *
     * @param model The Model object used to pass data to the view, including the
     *              new User object.
     * @return The name of the view ("add-user") to render the form for adding a new
     *         user.
     */
    @GetMapping("manage/add-user")
    public String showAddUserForm(Model model) {
        log.info("GET localhost:8080/web/manage/add-user -> showAddUserForm() is called");

        model.addAttribute("user", new User());

        return "add-user";
    }

    /**
     * Handles POST requests for the "manage/add-user" endpoint.
     * 
     * This method adds a new user with the provided details. It receives the user's
     * information as request parameters, creates a new User instance, sets its
     * properties,
     * and then calls the API controller to create the user. Finally, it populates
     * the model
     * with all users and redirects to the user overview page.
     *
     * @param firstName The first name of the user to be added.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user.
     * @param password  The password for the user's account.
     * @param organizer A boolean indicating if the user is an organizer.
     * @param model     The Model object for passing data to the view.
     * @return A redirection string to the "users" overview page.
     */
    @PostMapping("manage/add-user")
    public String addUser(@RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("organizer") boolean organizer,
            Model model) {

        // Create new user instance
        User user = new User();

        log.info("POST localhost:8080/web/manage/add-user -> addUser() is called: {}", user.getID());

        // Insert the parameters into the user instance
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setOrganizer(organizer);

        // Add the user to the user list
        apiController.createUser(user);

        // Redirect the user to the user overview
        model.addAttribute("users", apiController.getAllUsers());

        // Redirect the user to the user overview
        return "redirect:/web/users";
    }

    /**
     * Handles GET requests for checking if a user exists, based on their userID.
     *
     * This method is mapped to the "manage/check-user/{userID}" endpoint and is
     * responsible
     * for verifying the existence of a user with the given userID. It queries the
     * user
     * details through the API controller and returns a JSON response indicating
     * whether
     * the user exists or not.
     *
     * @param userID The unique identifier (UUID) of the user to check.
     * @return A {@link ResponseEntity} containing a JSON object with a key "exists"
     *         and a boolean value indicating whether the user exists or not.
     */
    @GetMapping("manage/check-user/{userID}")
    public ResponseEntity<?> checkUser(@PathVariable("userID") UUID userID) {
        log.info("GET localhost:8080/web/manage/check-user/{} -> checkUser() is called: {}", userID, userID);

        ResponseEntity<?> response = apiController.getUser(userID);

        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok("{\"exists\": true}");
        } else {
            return ResponseEntity.ok("{\"exists\": false}");
        }
    }

    /**
     * Handles DELETE requests for the "manage/delete-user/{userID}" endpoint.
     * 
     * This method is responsible for deleting a user identified by their userID. It
     * makes a
     * call to the API controller to delete the user and logs the outcome. Depending
     * on the
     * result of the deletion process, it redirects to different views.
     *
     * @param userID The unique identifier (UUID) of the user to be deleted.
     * @param model  The Model object used to pass data to the view.
     * @return A redirection string: to the manage page if deletion is successful,
     *         or
     *         to the user overview page if deletion fails.
     */
    @DeleteMapping("manage/delete-user/{userID}")
    public String deleteUser(@PathVariable("userID") UUID userID, Model model) {
        log.info("DELETE localhost:8080/web/manage/delete-user/{} -> deleteUser() is called: {}", userID, userID);

        ResponseEntity<?> response = apiController.deleteUser(userID);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("WebController: User ID: {} deleted", userID);
            return "redirect:/web/manage";
        } else {
            log.warn("WebController: Error deleting user ID: {}. Status code: {}", userID, response.getStatusCode());
            return "redirect:/web/users";
        }
    }

}
