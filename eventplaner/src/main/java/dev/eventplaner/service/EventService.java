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
     * Creates a new event in the repository.
     *
     * This method sends a POST request to the repository with the new event as the
     * body. The URL for the repository is constructed by appending "/events" to the
     * base API URL. If the repository returns an error, the method catches the
     * HttpClientErrorException and returns a ResponseEntity with the error message
     * and status code from the exception.
     *
     * @param event The new event to be created.
     * @return A ResponseEntity containing the response from the repository. If the
     *         creation was successful, the status code will be HttpStatus.CREATED.
     *         If an error occurred, the status code and error message from the
     *         HttpClientErrorException will be returned.
     */
    public ResponseEntity<?> create(Event event) {
        log.info("Eventplaner EventService -> create() is called: {}", event.getID());

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
     * Retrieves all events from the repository as DTOs.
     *
     * This method sends a GET request to the repository. The URL for the repository
     * is constructed by appending "/events" to the base API URL. If the repository
     * returns an error, the method catches the HttpClientErrorException and returns
     * a ResponseEntity with the error message and status code from the exception.
     *
     * @return A ResponseEntity containing the response from the repository. If the
     *         retrieval was successful, the status code will be HttpStatus.OK and
     *         the body will contain a list of event DTOs. If an error occurred, the
     *         status code and error message from the HttpClientErrorException will
     *         be returned.
     */
    public ResponseEntity<?> getAllDTO() {
        log.info("Eventplaner EventService -> getAllDTO() is called");

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
     * Retrieves a specific event from the repository.
     *
     * This method sends a GET request to the repository to retrieve an event with
     * the specified ID. The URL for the repository is constructed by appending
     * "/events/" and the event ID to the base API URL. If the repository returns an
     * error, the method catches the HttpClientErrorException and returns a
     * ResponseEntity with the error message and status code from the exception.
     *
     * @param eventID The ID of the event to be retrieved.
     * @return A ResponseEntity containing the response from the repository. If the
     *         retrieval was successful, the status code will be HttpStatus.OK and
     *         the body will contain the event. If an error occurred, the status
     *         code and error message from the HttpClientErrorException will be
     *         returned.
     */
    public ResponseEntity<?> getEvent(UUID eventID) {
        log.info("Eventplaner EventService -> getEvent() is called: {}", eventID);

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
     * Updates an existing event in the repository.
     * The method sends a PUT request to the repository with the updated event.
     * If the repository returns an error, the method catches the
     * HttpClientErrorException and returns its response.
     *
     * @param event The updated event to send to the repository.
     * @return A ResponseEntity containing the response from the repository.
     */
    public ResponseEntity<?> update(Event event) {
        log.info("Eventplaner EventService -> update() is called: {}", event.getID());

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
     * Deletes a specific event from the repository.
     *
     * This method sends a DELETE request to the repository to delete an event with
     * the specified ID. The URL for the repository is constructed by appending
     * "/events/" and the event ID to the base API URL. If the repository returns an
     * error, the method catches the HttpClientErrorException and returns a
     * ResponseEntity with the error message and status code from the exception.
     *
     * @param eventID The ID of the event to be deleted.
     * @return A ResponseEntity containing the response from the repository. If the
     *         deletion was successful, the status code will be
     *         HttpStatus.NO_CONTENT. If an error occurred, the status code and
     *         error message from the HttpClientErrorException will be returned.
     */
    public ResponseEntity<?> delete(UUID eventID) {
        log.info("Eventplaner EventService -> delete() is called: {}", eventID);

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
     * Adds a user to a specific event in the repository.
     *
     * This method sends a PUT request to the repository to add a user to an event
     * with the specified IDs. The URL for the repository is constructed by
     * appending "/events/", the event ID, "/add/", and the user ID to the base API
     * URL. If the repository returns an error, the method catches the
     * HttpClientErrorException and returns a ResponseEntity with the error message
     * and status code from the exception.
     *
     * @param eventID The ID of the event to which the user will be added.
     * @param userID  The ID of the user to be added to the event.
     * @return A ResponseEntity containing the response from the repository. If the
     *         addition was successful, the status code will be HttpStatus.OK. If an
     *         error occurred, the status code and error message from the
     *         HttpClientErrorException will be returned.
     */
    public ResponseEntity<?> addUser(UUID eventID, UUID userID) {
        log.info("Eventplaner EventService -> addUser() is called: {} and {}", eventID, userID);

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
     * Removes a user from a specific event in the repository.
     *
     * This method sends a PUT request to the repository to remove a user from an
     * event with the specified IDs. The URL for the repository is constructed by
     * appending "/events/", the event ID, "/remove/", and the user ID to the base
     * API URL. If the repository returns an error, the method catches the
     * HttpClientErrorException and returns a ResponseEntity with the error message
     * and status code from the exception.
     *
     * @param eventID The ID of the event from which the user will be removed.
     * @param userID  The ID of the user to be removed from the event.
     * @return A ResponseEntity containing the response from the repository. If the
     *         removal was successful, the status code will be HttpStatus.OK. If an
     *         error occurred, the status code and error message from the
     *         HttpClientErrorException will be returned.
     */
    public ResponseEntity<?> removeUser(UUID eventID, UUID userID) {
        log.info("Eventplaner EventService -> removeUser() is called: {} and {}", eventID, userID);

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
     * Removes a user from all events in the repository.
     *
     * This method sends a PUT request to the repository to remove a user from all
     * events. The URL for the repository is constructed by appending
     * "/events/remove/" and the user ID to the base API URL. If the repository
     * returns an error, the method catches the HttpClientErrorException and returns
     * a ResponseEntity with the error message and status code from the exception.
     *
     * @param userID The ID of the user to be removed from all events.
     * @return A ResponseEntity containing the response from the repository. If the
     *         removal was successful, the status code will be HttpStatus.OK. If an
     *         error occurred, the status code and error message from the
     *         HttpClientErrorException will be returned.
     */
    public ResponseEntity<?> removeUserFromAllEvents(UUID userID) {
        log.info("Eventplaner EventService -> removeUserFromAllEvents() is called: {}", userID);

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
     * Adds a rating for a specific event from a specific user in the repository.
     *
     * This method sends a PUT request to the repository to add a rating for an
     * event with the specified IDs. The URL for the repository is constructed by
     * appending "/events/", the event ID, the user ID, and the rating to the base
     * API URL. If the repository returns an error, the method catches the
     * HttpClientErrorException and returns a ResponseEntity with the error message
     * and status code from the exception.
     *
     * @param eventID The ID of the event to be rated.
     * @param userID  The ID of the user who is rating the event.
     * @param rating  The rating to be added.
     * @return A ResponseEntity containing the response from the repository. If the
     *         addition was successful, the status code will be HttpStatus.OK. If an
     *         error occurred, the status code and error message from the
     *         HttpClientErrorException will be returned.
     */
    public ResponseEntity<?> addRating(UUID eventID, UUID userID, int rating) {
        log.info("Eventplaner EventService -> addRating() is called: {} and {} and {}", eventID, userID, rating);

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
