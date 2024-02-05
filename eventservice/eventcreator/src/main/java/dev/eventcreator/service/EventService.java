package dev.eventcreator.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dev.eventcreator.model.Event;
import dev.eventcreator.model.EventDTO;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    @Value("${repository.url}")
    private String apiUrl;

    /**
     * Creates a new event by making a POST request to the
     * specified API endpoint.
     *
     * @param event The Event object representing the event to be created.
     * @return A ResponseEntity containing the response from the server, which may
     *         include created event data
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> create(Event event) {
        log.info("Event Created: {}, {}", event.getName(), event.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> request = new HttpEntity<Event>(event, headers);

        try {
            return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Retrieves a list of all events by making a GET request
     * to the specified API endpoint.
     *
     * @return A String representation of the response from the server, which may
     *         include a list of events
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public String getAll() {
        log.info("get all Events");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class).getBody().toString();
        } catch (HttpClientErrorException e) {
            return e.getResponseBodyAsString();
        }
    }

    /**
     * Retrieves a list of all events as Data Transfer Objects
     * by making a GET request to
     * the specified API endpoint.
     *
     * @return A ResponseEntity containing the response from the server, which may
     *         include a list of event DTOs
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> getAllDTO() {
        log.info("get all Events as DTO");

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                return response;
            }
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }

        String body = response.getBody();
        Collection<Event> values = Event.collectionFromJson(body);

        log.info("values: {}", body);

        for (Event event : values) {
            log.info("Event: {}", event.toString());
        }

        Collection<EventDTO> eventDTO = new ArrayList<>();
        if (values != null) {
            for (Event event : values) {
                EventDTO newEvent = new EventDTO(event);
                log.info("newEvent: {}", newEvent.getID());
                eventDTO.add(newEvent);
            }
        }
        ResponseEntity<?> newResponse = new ResponseEntity<>(eventDTO, HttpStatus.OK);
        return newResponse;
    }

    /**
     * Retrieves event information as a String by making a GET
     * request to the specified API endpoint,
     * using the provided eventID.
     *
     * @param eventID The UUID of the event to be retrieved.
     * @return A String representation of the response from the server, which may
     *         include event data
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public String getEventString(UUID eventID) {
        log.info("get event by eventID: {}", eventID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class).getBody().toString();
        } catch (HttpClientErrorException e) {
            return e.getResponseBodyAsString();
        }
    }

    /**
     * Retrieves event information by making a GET request to
     * the specified API endpoint,
     * using the provided eventID.
     *
     * @param eventID The UUID of the event to be retrieved.
     * @return A ResponseEntity containing the response from the server, which may
     *         include event data
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> getEvent(UUID eventID) {
        log.info("get event by eventID: {}", eventID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Replaces an existing event with updated event data by
     * making a PUT request to the specified API endpoint.
     *
     * @param event The Event object representing the updated event data.
     * @return A ResponseEntity containing the response from the server, which may
     *         include updated event data
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> replace(Event event) {
        log.info("update event: {}", event.getID());
        log.info("event Participants: {}", event.getParticipants());
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + event.getID();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> request = new HttpEntity<Event>(event, headers);

        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Deletes an event by making a DELETE request to the
     * specified API endpoint,
     * using the provided eventID.
     *
     * @param eventID The UUID of the event to be deleted.
     * @return A ResponseEntity containing the response from the server, which may
     *         include success information
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> delete(UUID eventID) {
        log.info("delete eventID: {}", eventID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Adds a user to an event by making necessary checks and
     * updates.
     *
     * @param eventID The UUID of the event to which the user will be added.
     * @param userID  The UUID of the user to be added to the event.
     * @return The Event object representing the updated event after adding the
     *         user.
     * @throws IllegalArgumentException If the user is already a participant in the
     *                                  event or if the participant limit is
     *                                  reached.
     */
    public Event addUser(UUID eventID, UUID userID) throws IllegalArgumentException {
        log.info("addUser: eventID={}, userID={}", eventID, userID);
        String eventString = getEventString(eventID);
        Event event = Event.eventFromJson(eventString).setID(eventID);

        if (event.contains(userID)) {
            throw new IllegalArgumentException("User is already event participant.");
        }
        if (!event.addParticipant(userID)) {
            throw new IllegalArgumentException("Participant limit reached.");
        }
        log.info("addUser, participants: {}", event.getParticipants());
        replace(event);
        return event;
    }

    /**
     * Removes a user from an event by making necessary checks
     * and updates.
     *
     * @param eventID The UUID of the event from which the user will be removed.
     * @param userID  The UUID of the user to be removed from the event.
     * @return The Event object representing the updated event after removing the
     *         user, or null if the user was not a participant.
     */
    public Event removeUser(UUID eventID, UUID userID) {
        log.info("removeUser: eventID={}, user={}", eventID, userID);
        String eventString = getEventString(eventID);
        Event event = Event.eventFromJson(eventString);

        if (!event.removeParticipant(userID)) {
            return null;
        }

        log.info("removeUser: participants{}", event.getParticipants());
        replace(event);
        return event;
    }

    /**
     * Removes a user with the given userID from all events on a remote server where
     * the user is a participant.
     *
     * @param userID The UUID of the user to be removed from all events.
     */
    public void removeUser(UUID userID) {
        log.info("removeUser: userId={}", userID);
        for (Event event : Event.collectionFromJson(getAll())) {
            if (event.removeParticipant(userID)) {
                replace(event);
            }
        }
    }

    /**
     * Adds a rating for a user on a specific event, making
     * necessary checks and updates.
     *
     * @param eventID The UUID of the event to which the rating will be added.
     * @param userID  The UUID of the user for whom the rating is being added.
     * @param rating  The rating value to be added.
     * @return The Event object representing the updated event after adding the
     *         rating.
     * @throws NotFoundException        If the event or user is not found, or the
     *                                  user is not a participant.
     * @throws IllegalArgumentException If the rating value is not valid.
     */
    public Event addRating(UUID eventID, UUID userID, int rating) throws NotFoundException, IllegalArgumentException {
        log.info("addRating: eventID={}, userID={}, rating={}", eventID, userID, rating);
        String eventString = getEventString(eventID);
        Event event = Event.eventFromJson(eventString);
        if (event == null || !event.contains(userID)) {
            throw new NotFoundException();
        }

        if (!event.rate(userID, rating)) {
            throw new IllegalArgumentException("Rating not valid.");
        }
        replace(event);
        return event;
    }

    /**
     * Updates an existing event with new event data, making
     * necessary checks and updates.
     *
     * @param newEvent The Event object representing the updated event data.
     * @return A ResponseEntity containing the response from the server, which may
     *         include updated event data
     *         or an error message in case of failure.
     * @throws IllegalArgumentException If the new event data contains invalid
     *                                  values.
     */
    public ResponseEntity<?> updateEvent(Event newEvent) {
        log.info("update Event: {}", newEvent.getID());

        Event event = Event.eventFromJson(getEvent(newEvent.getID()).getBody().toString());

        if (event == null) {
            return replace(newEvent);
        }

        if (newEvent.getName() != null) {
            event.setName(newEvent.getName());
        }
        if (newEvent.getDescription() != null) {
            event.setDescription(newEvent.getDescription());
        }
        if (newEvent.getDateTime() != null) {
            event.setDateTime(newEvent.getDateTime());
        }
        if (newEvent.getLocation() != null) {
            event.setLocation(newEvent.getLocation());
        }
        if (newEvent.getMaxParticipants() != 10) {
            event.setMaxParticipants(newEvent.getMaxParticipants());
        }
        if (newEvent.getOrganizerUserID() != null) {
            event.setOrganizerUserID(newEvent.getOrganizerUserID());
        }

        return replace(event);
    }
}
