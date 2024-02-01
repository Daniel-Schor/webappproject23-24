package dev.eventcreator.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.eventcreator.model.Event;
import dev.eventcreator.service.EventService;

import org.springframework.web.bind.annotation.PutMapping;

/**
 * This class is a REST controller for managing events.
 * It includes methods for creating, retrieving, updating, deleting, and rating
 * events, as well as adding and removing participants.
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

    public ResponseEntity<?> checkProcessability(Event event) {
        String detail = Event.isValid(event);
        if (detail != null) {
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);
            pd.setInstance(URI.create("/events"));
            pd.setTitle("JSON Object Error");
            return ResponseEntity.unprocessableEntity().body(pd);
        }
        return null;
    }

    /**
     * Retrieves a list of all events.
     *
     * This method is mapped to the GET request at '/events' and returns all
     * available events.
     * It produces a response in JSON format. The actual retrieval of events is
     * delegated to the
     * eventService's 'getAllDTO' method.
     *
     * @return A ResponseEntity containing a list of all events in JSON format. The
     *         response
     *         includes the appropriate HTTP status code based on the success or
     *         failure of the
     *         event retrieval operation.
     */
    @GetMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllEvents() {
        log.info("GET localhost:8081/events -> getAllEvents() is called");

        return eventService.getAllDTO();
    }

    /**
     * Retrieves the details of a specific event identified by its UUID.
     *
     * This method is mapped to the GET request at '/events/{eventID}' and is
     * responsible for fetching
     * the details of an event specified by the provided eventID. The response is in
     * JSON format.
     * The actual retrieval process is handled by the eventService's 'getEvent'
     * method.
     *
     * @param eventID The UUID of the event to be retrieved.
     * @return A ResponseEntity containing the event details in JSON format.
     *         The response includes the appropriate HTTP status code based on the
     *         success or failure
     *         of the event retrieval operation.
     */
    @GetMapping(value = "/events/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getEvent(@PathVariable("eventID") UUID eventID) {
        log.info("GET localhost:8081/events/{} -> getEvent({}) is called", eventID, eventID);

        return eventService.getEvent(eventID);
    }

    /**
     * Creates a new event based on the provided event details.
     *
     * This method is mapped to the POST request at '/events'. It is responsible for
     * creating
     * a new event using the details provided in the request body. The request and
     * response are
     * both in JSON format. The event creation process is handled by the
     * eventService's 'create' method.
     *
     * @param event The Event object containing the information for the new event.
     * @return A ResponseEntity indicating the outcome of the event creation
     *         process.
     *         The response includes the appropriate HTTP status code and any
     *         relevant
     *         event data in JSON format.
     */
    @PostMapping(value = "/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        log.info("POST localhost:8081/events -> createEvent(Name: {}) is called", event.getName());

        return eventService.create(event);
    }

    /**
     * Updates an existing event with new information.
     *
     * This method handles a PUT request at '/events/{eventID}' to update an event.
     * It consumes and produces
     * JSON format. The event to update is identified by eventID in the URL, and the
     * new event details are
     * provided in the request body. Before updating, it checks if the event is
     * processable using the
     * 'checkProcessability' method. If processable, it updates the event using the
     * eventService's 'update' method.
     *
     * @param eventID The UUID of the event to be updated.
     * @param event   The Event object containing the new details for the update.
     * @return A ResponseEntity indicating the result of the update operation,
     *         including the appropriate
     *         HTTP status code and updated event data in JSON format if successful.
     */
    @PutMapping(value = "/events/{eventID}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateEvent(@PathVariable("eventID") UUID eventID, @RequestBody Event event) {
        log.info("PUT localhost:8081/events/{} -> updateEvent({}, Name: {}) is called", eventID, event.getName());

        ResponseEntity<?> response = checkProcessability(event);
        if (response == null) {
            return eventService.update(event.setID(eventID));
        }
        return response;
    }

    /**
     * Deletes an event identified by its UUID.
     *
     * This method responds to a DELETE request at '/events/{eventID}'. It is
     * responsible for deleting
     * the event corresponding to the given eventID. The deletion process is
     * delegated to the eventService's
     * 'delete' method. The method produces a response in JSON format.
     *
     * @param eventID The UUID of the event to be deleted.
     * @return A ResponseEntity indicating the result of the delete operation,
     *         including the appropriate
     *         HTTP status code. The response may also include additional
     *         information about the deletion process.
     */
    @DeleteMapping(value = "/events/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteEvent(@PathVariable("eventID") UUID eventID) {
        log.info("DELETE localhost:8081/events/{} -> deleteEvent({}) is called", eventID, eventID);

        return eventService.delete(eventID);
    }

    /**
     * Adds a participant to an event.
     *
     * This method is mapped to a PUT request at '/events/{eventID}/add/{userID}'
     * and handles adding a user
     * as a participant to an event. It first checks if the event can accommodate
     * more participants based on the
     * maximum allowed participants. If the event is at capacity or the event
     * doesn't exist, the method returns
     * an appropriate error response. Otherwise, it adds the user to the event.
     *
     * @param eventID The UUID of the event to which the participant is to be added.
     * @param userID  The UUID of the user to be added as a participant.
     * @return A ResponseEntity object. If successful, it includes the updated Event
     *         object in the response with an OK status.
     *         Otherwise, it returns a bad request or the status from the event
     *         retrieval attempt.
     */
    @PutMapping(value = "/events/{eventID}/add/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addParticipant(@PathVariable("eventID") UUID eventID,
            @PathVariable("userID") UUID userID) {
        log.info("PUT localhost:8081/events/{}/add/{} -> addParticipant({}, {}) is called", eventID, userID, eventID,
                userID);

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
     * This method handles a PUT request at '/events/{eventID}/remove/{userID}' and
     * is responsible for removing a user
     * as a participant from a specified event. It first checks if the event exists.
     * If the event is not found or other
     * errors occur, it returns an appropriate response. If the event exists, it
     * proceeds to remove the specified user.
     *
     * @param eventID The UUID of the event from which the participant is to be
     *                removed.
     * @param userID  The UUID of the user to be removed as a participant.
     * @return A ResponseEntity object. If the user is successfully removed, it
     *         includes the updated Event object in the response with an OK status.
     *         If the event is not found, it returns a not found status. Other
     *         errors result in the corresponding error response.
     */
    @PutMapping(value = "/events/{eventID}/remove/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> removeParticipant(@PathVariable("eventID") UUID eventID,
            @PathVariable("userID") UUID userID) {
        log.info("PUT localhost:8081/events/{}/remove/{} -> removeParticipant({}, {}) is called", eventID, userID,
                eventID,
                userID);

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

    /**
     * Removes a participant from all events.
     *
     * This method, mapped to a PUT request at '/events/remove/{userID}', is
     * responsible for removing a user,
     * identified by userID, from all events they are participating in. The
     * operation is executed by the
     * eventService's 'removeUser' method. After successfully removing the user from
     * all events, it returns a
     * no content status as confirmation.
     *
     * @param userID The UUID of the user to be removed from all events.
     * @return A ResponseEntity with HttpStatus.NO_CONTENT, indicating that the user
     *         has been successfully
     *         removed from all events.
     */
    @PutMapping(value = "/events/remove/{userID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> removeParticipantFromAllEvents(@PathVariable("userID") UUID userID) {
        log.info("PUT localhost:8081/events/remove/{} -> removeParticipant({}) is called", userID, userID);

        eventService.removeUser(userID);
        return ResponseEntity.noContent().build();
    }

    /**
     * Rates an event by a user with a specified rating value.
     *
     * This method, mapped to a PUT request at
     * '/events/{eventID}/{userID}/{rating}', allows a user
     * to rate an event. It first validates that the rating is within an acceptable
     * range (e.g., 1 to 5).
     * If the rating is not valid, it returns a bad request response. It then checks
     * if the event exists.
     * If the event exists and the rating is valid, it proceeds to add the rating to
     * the event. If the
     * event is not found or other errors occur, it returns an appropriate response.
     *
     * @param eventID The UUID of the event to be rated.
     * @param userID  The UUID of the user giving the rating.
     * @param rating  The rating value, expected to be within a specific range.
     * @return A ResponseEntity object. If the rating is successfully added, it
     *         includes the updated Event object
     *         in the response with an OK status. Invalid ratings return a bad
     *         request status, and errors
     *         in finding the event return a not found status or the corresponding
     *         error response.
     */
    @PutMapping(value = "/events/{eventID}/{userID}/{rating}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> rateEvent(@PathVariable("eventID") UUID eventID, @PathVariable("userID") UUID userID,
            @PathVariable("rating") int rating) {
        log.info("PUT localhost:8081/events/{}/{}/{} -> rateEvent({}, {}, {}) is called", eventID, userID, rating,
                eventID, userID, rating);

        if (rating <= 0 || rating > 5) {
            return ResponseEntity.badRequest().build();
        }

        ResponseEntity<?> response = eventService.getEvent(eventID);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        Event event = eventService.addRating(eventID, userID, rating);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

}
