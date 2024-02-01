package dev.eventplaner.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dev.eventplaner.model.Event;

/**
 * This class provides services for managing events.
 * It includes methods for creating, retrieving, updating, and deleting events.
 * It also includes methods for adding and removing users from events, and for
 * adding ratings to events.
 */
@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    // Read the URL of the external API from properties file.
    @Value("${eventservice.url}")
    String apiUrl;

    /**
     * Creates a new event.
     *
     * @param event The event to create.
     * @return The response from the server.
     */
    public ResponseEntity<?> create(Event event) {
        log.info("Eventplaner EventService -> create() is called: {}", event.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> request = new HttpEntity<Event>(event, headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        return response;
    }

    /**
     * Retrieves all events as DTOs.
     *
     * @return The response from the server.
     */
    public ResponseEntity<?> getAllDTO() {
        log.info("Eventplaner EventService -> getAllDTO() is called");

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        return response;
    }

    /**
     * Retrieves an event by its ID.
     *
     * @param eventID The ID of the event to retrieve.
     * @return The response from the server.
     */
    public ResponseEntity<?> getEvent(UUID eventID) {
        log.info("Eventplaner EventService -> getEvent() is called: {}", eventID);


        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        return response;
    }

    /**
     * Updates an event.
     *
     * @param eventID      The ID of the event to update.
     * @param event        The new event data.
     * @param responseType The type of the response.
     * @return The response from the server.
     */
    public ResponseEntity<?> update(Event event) {
        log.info("Eventplaner EventService -> update() is called: {}", event.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + event.getID();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> request = new HttpEntity<>(event, headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }

        return response;
    }

    /**
     * Deletes an event.
     *
     * @param eventID The ID of the event to delete.
     * @return The response from the server.
     */
    public ResponseEntity<?> delete(UUID eventID) {
        log.info("Eventplaner EventService -> delete() is called: {}", eventID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
        return response;
    }

    /**
     * Adds a user to an event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user to add.
     * @return The response from the server.
     */
    public ResponseEntity<?> addUser(UUID eventID, UUID userID) {
        log.info("Eventplaner EventService -> addUser() is called: {} and {}", eventID, userID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID + "/add/" + userID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }

        return response;
    }

    /**
     * Removes a user from an event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user to remove.
     * @return The response from the server.
     */
    public ResponseEntity<?> removeUser(UUID eventID, UUID userID) {
        log.info("Eventplaner EventService -> removeUser() is called: {} and {}", eventID, userID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID + "/remove/" + userID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }

        return response;
    }

    /**
     * Removes a user from all events.
     *
     * @param userID The ID of the user to remove.
     * @return The response from the server.
     */
    public ResponseEntity<?> removeUserFromAllEvents(UUID userID) {
        log.info("Eventplaner EventService -> removeUserFromAllEvents() is called: {}", userID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/remove/" + userID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }

        return response;
    }

    /**
     * Adds a rating to an event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user adding the rating.
     * @param rating  The rating to add.
     * @return The response from the server.
     */
    public ResponseEntity<?> addRating(UUID eventID, UUID userID, int rating) {
        log.info("Eventplaner EventService -> addRating() is called: {} and {} and {}", eventID, userID, rating);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID + "/" + userID + "/" + rating;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            response = new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }

        return response;
    }

}
