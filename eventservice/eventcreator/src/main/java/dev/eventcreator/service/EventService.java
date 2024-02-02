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

/**
 * This class provides services for managing events.
 * It includes methods for creating, retrieving, updating, and deleting events.
 */
@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    @Value("${repository.url}")
    private String apiUrl;

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
     * Retrieves all events from the repository.
     *
     * This method sends a GET request to the repository to retrieve all events. The
     * URL for the repository is constructed by appending "/events" to the base API
     * URL. If the repository returns an error, the method catches the
     * HttpClientErrorException and returns the error message and status code from
     * the exception.
     *
     * @return A string representation of the response body if the retrieval was
     *         successful, or an error message if an error occurred.
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
     * Retrieves all events from the repository, converts them to DTOs, and returns
     * them.
     *
     * This method sends a GET request to the repository to retrieve all events. It
     * then converts each event to a DTO and adds it to a collection. If the
     * repository returns an error, the method catches the HttpClientErrorException
     * and returns a ResponseEntity with the error message and status code from the
     * exception.
     *
     * @return A ResponseEntity containing a collection of EventDTOs if the
     *         retrieval was successful, or an error message and status code if an
     *         error occurred.
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
     * Retrieves a specific event from the repository and returns it as a string.
     *
     * This method sends a GET request to the repository to retrieve an event with
     * the specified ID. The URL for the repository is constructed by appending
     * "/events/" and the event ID to the base API URL. If the repository returns an
     * error, the method catches the HttpClientErrorException and returns the error
     * message as a string.
     *
     * @param eventID The ID of the event to be retrieved.
     * @return A string representation of the event if the retrieval was successful,
     *         or an error message if an error occurred.
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

    // TODO check javadoc
    /**
     * Updates an existing event in the repository.
     *
     * This method sends a PUT request to the repository with the event as the body.
     * If the repository returns an error, the method catches the
     * HttpClientErrorException and returns its response.
     *
     * @param event The event to be updated.
     * @return A ResponseEntity containing the response from the repository.
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
     * Adds a user to a specific event.
     *
     * This method retrieves an event with the specified ID, adds a user with the
     * specified ID to the event's participants, and updates the event in the
     * repository. If the event does not exist or the user cannot be added to the
     * event's participants, the method throws an IllegalArgumentException.
     *
     * @param eventID The ID of the event to which the user will be added.
     * @param userID  The ID of the user to be added to the event.
     * @return The updated event.
     * @throws IllegalArgumentException If the event does not exist or the user
     *                                  cannot be added to the event's participants.
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
     * Removes a user from a specific event.
     *
     * This method retrieves an event with the specified ID, removes a user with the
     * specified ID from the event's participants, and updates the event in the
     * repository. If the event does not exist or the user cannot be removed from
     * the event's participants, the method returns null.
     *
     * @param eventID The ID of the event from which the user will be removed.
     * @param userID  The ID of the user to be removed from the event.
     * @return The updated event if the user was successfully removed, or null if
     *         the event does not exist or the user could not be removed.
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
     * Removes a user from all events.
     *
     * This method retrieves all events, and for each event, it removes a user with
     * the specified ID from the event's participants and updates the event in the
     * repository. If the user is not a participant of an event, that event is not
     * updated.
     *
     * @param userID The ID of the user to be removed from all events.
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
     * Adds a rating for a specific event from a specific user.
     *
<<<<<<< HEAD
     * This method retrieves an event with the specified ID, and if the event exists
     * and contains the user with the specified ID, it adds the specified rating for
     * the user and updates the event in the repository. If the event does not exist
     * or does not contain the user, the method returns null.
     *
     * @param eventID The ID of the event to be rated.
     * @param userID  The ID of the user who is rating the event.
     * @param rating  The rating to be added.
     * @return The updated event if the rating was successfully added, or null if
     *         the event does not exist or does not contain the user.
=======
     * @param eventID The ID of the event.
     * @param userID  The ID of the user.
     * @param rating  The rating to add.
     * @return A string representation of the updated event.
     * @throws NotFoundException
>>>>>>> ecb2b325d8ca12bcd968b360bcca05bff0743b6d
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

    // TODO javadoc
    public ResponseEntity<?> updateEvent(Event newEvent){
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
