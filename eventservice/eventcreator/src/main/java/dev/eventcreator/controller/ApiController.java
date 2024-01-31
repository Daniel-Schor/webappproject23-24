package dev.eventcreator.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.eventcreator.model.Event;
import dev.eventcreator.service.EventService;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * This class is a REST controller for managing events.
 * It includes methods for creating, retrieving, updating, deleting, and rating events, as well as adding and removing participants.
 */
@RestController
public class ApiController {

    // Logger instance for this class, used to log system messages, warnings, and
    // errors.
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    // Autowired annotation is used to automatically inject the EventService and
    // instance into this class.
    @Autowired
    private EventService eventService;

    /**
     * Removes a participant from all events.
     *
     * @param userID The ID of the user to be removed.
     * @return A ResponseEntity indicating that the operation was successful.
     */
    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllEvents() {
        log.info("Get all events");

        return eventService.getAllDTO();
    }

    /**
     * Retrieves the event with the specified event ID.
     *
     * @param eventID the ID of the event to retrieve
     * @return a ResponseEntity containing the event if found, or no content if not
     *         found
     */
    @GetMapping(value = "/events/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getEvent(@PathVariable("eventID") UUID eventID) {
        log.info("Get all users");

        return eventService.getEvent(eventID);
    }

    /**
     * Creates a new event.
     *
     * @param event The event object containing the details of the event to be
     *              created.
     * @return ResponseEntity representing the HTTP response with the created event
     *         or an error message.
     */
    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        log.info("Create new Event: {}", event.getName());
        /*
         * TODO implement this in repository
         * if (event.getName() == null || event.getName().isEmpty()) {
         * String detail = "Event name must not be null or empty";
         * ProblemDetail pd =
         * ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
         * pd.setInstance(URI.create("/events"));
         * pd.setTitle("Event creation error");
         * return ResponseEntity.unprocessableEntity().body(pd);
         * }
         */
        return eventService.create(event);
    }

    /**
     * Updates an event with the given event ID.
     *
     * @param eventID The ID of the event to be updated.
     * @param event   The updated event object.
     * @return ResponseEntity containing the updated event if successful, or
     *         ResponseEntity with status 404 if the event is not found.
     */
    @PutMapping(value = "/events/{eventID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateEvent(@PathVariable("eventID") UUID eventID, @RequestBody Event event) {
        log.info("Update event: {}", eventID);

        return eventService.update(event.setID(eventID));
    }

    /**
     * Deletes an event with the specified event ID.
     *
     * @param eventID The ID of the event to be deleted.
     * @return A ResponseEntity containing the deleted event if successful, or a not
     *         found response if the event does not exist.
     */
    @DeleteMapping(value = "/events/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteEvent(@PathVariable("eventID") UUID eventID) {
        log.debug("deleteEvent() is called");

        return eventService.delete(eventID);
    }

    /**
     * Adds a participant to an event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user to be added as a participant.
     * @return The ResponseEntity containing the updated event information.
     */
    @PutMapping(value = "/events/{eventID}/add/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addParticipant(@PathVariable("eventID") UUID eventID,
            @PathVariable("userID") UUID userID) {
        log.debug("addParticipant() is called");

        int maxParticipants = Event.eventFromJson(eventService.getEventString(eventID)).getMaxParticipants();
        int participants = Event.eventFromJson(eventService.getEventString(eventID)).getParticipants().size();
        if (maxParticipants <= participants + 1) {
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<?> response = eventService.getEvent(eventID);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        Event event = eventService.addUser(eventID, userID);

        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    /**
     * Removes a participant from an event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user to be removed.
     * @return A ResponseEntity containing the updated event if successful, or a not
     *         found response if the event ID is invalid.
     */
    @PutMapping(value = "/events/{eventID}/remove/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> removeParticipant(@PathVariable("eventID") UUID eventID,
            @PathVariable("userID") UUID userID) {
        log.debug("removeParticipant() is called");

        ResponseEntity<?> response = eventService.getEvent(eventID);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        Event event = eventService.removeUser(eventID, userID);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Event>(event, HttpStatus.OK);
    }

    @PutMapping(value = "/events/remove/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> removeParticipantFromAllEvents(@PathVariable("userID") UUID userID) {
        log.debug("removeParticipant() is called");

        eventService.removeUser(userID);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the rating of an event by a user.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user.
     * @param rating  The new rating for the event.
     * @return ResponseEntity containing the updated Event object if successful, or
     *         a not found response if the event ID is invalid.
     */
    @PutMapping(value = "/events/{eventID}/{userID}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> rateEvent(@PathVariable("eventID") UUID eventID, @PathVariable("userID") UUID userID,
            @PathVariable("rating") int rating) {
        log.debug("rateEvent() is called");

        if (rating <= 0 || rating > 5) {
            return ResponseEntity.badRequest().build();
        }

        ResponseEntity<?> response = eventService.getEvent(eventID);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        String event = eventService.addRating(eventID, userID, rating);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<String>(event, HttpStatus.OK);
    }

}
