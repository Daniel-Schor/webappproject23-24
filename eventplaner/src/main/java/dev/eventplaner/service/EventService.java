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

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    // Read the URL of the external API from properties file.
    @Value("${eventservice.url}")
    String apiUrl;

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
        log.info("create() is called: {}", event.getID());

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
     * Retrieves a list of event Data Transfer Objects
     * by making a GET request to the
     * specified API endpoint.
     *
     * @return A ResponseEntity containing the response from the server, which may
     *         include a list of event DTOs
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> getAllDTO() {
        log.info("getAllDTO() is called");

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
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
        log.info("getEvent() is called: {}", eventID);

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
        log.info("replace() is called: {}", event.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + event.getID();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> request = new HttpEntity<>(event, headers);

        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Updates an event by making a PUT request to the specified
     * API endpoint.
     *
     * @param event The Event object representing the event to be updated.
     * @return A ResponseEntity containing the response from the server, which may
     *         include updated event data
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as invalid URL or server errors.
     */
    public ResponseEntity<?> update(Event event) {
        log.info("update() is called: {}", event.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/update/" + event.getID();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> request = new HttpEntity<>(event, headers);

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
        log.info("delete() is called: {}", eventID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Adds a user to an event by making a PUT request to the
     * specified API endpoint.
     *
     * @param eventID The UUID of the event to which the user will be added.
     * @param userID  The UUID of the user to be added to the event.
     * @return A ResponseEntity containing the response from the server, which may
     *         include success information
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> addUser(UUID eventID, UUID userID) {
        log.info("addUser() is called: {} and {}", eventID, userID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID + "/add/" + userID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Removes a user from an event by making a PUT request to
     * the specified API endpoint.
     *
     * @param eventID The UUID of the event from which the user will be removed.
     * @param userID  The UUID of the user to be removed from the event.
     * @return A ResponseEntity containing the response from the server, which may
     *         include success information
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> removeUser(UUID eventID, UUID userID) {
        log.info("removeUser() is called: {} and {}", eventID, userID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID + "/remove/" + userID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Removes a user from all events where the user is a
     * participant by making a PUT request
     * to the specified API endpoint.
     *
     * @param userID The UUID of the user to be removed from all events.
     * @return A ResponseEntity containing the response from the server, which may
     *         include success information
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> removeUserFromAllEvents(UUID userID) {
        log.info("removeUserFromAllEvents() is called: {}", userID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/remove/" + userID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

    /**
     * Adds a rating for a user on a specific event by making a
     * PUT request to the specified API endpoint.
     *
     * @param eventID The UUID of the event to which the rating will be added.
     * @param userID  The UUID of the user for whom the rating is being added.
     * @param rating  The rating value to be added.
     * @return A ResponseEntity containing the response from the server, which may
     *         include success information
     *         or an error message in case of failure.
     * @throws HttpClientErrorException If there is an issue with the HTTP request,
     *                                  such as an invalid URL or server errors.
     */
    public ResponseEntity<?> addRating(UUID eventID, UUID userID, int rating) {
        log.info("addRating() is called: {} and {} and {}", eventID, userID, rating);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID + "/" + userID + "/" + rating;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
        }
    }

}
