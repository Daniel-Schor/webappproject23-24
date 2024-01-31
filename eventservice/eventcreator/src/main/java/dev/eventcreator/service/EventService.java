package dev.eventcreator.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dev.eventcreator.model.ApiError;
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
     * Creates a new event.
     *
     * @param event The event to create.
     * @return The response from the repository.
     */
    public ResponseEntity<?> create(Event event) {
        log.info("Event Created: {}, {}", event.getName(), event.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> request = new HttpEntity<Event>(event, headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            if (response.getStatusCode() != HttpStatus.CREATED) {
                return response;
            }
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    /**
     * Retrieves all events.
     *
     * @return A string representation of all events.
     */
    // FIXME use this instead of getAllDTO; test this
    public String getAll() {
        log.info("get all Events");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response.getBody().toString();
    }

    /**
     * Retrieves all events as DTOs.
     *
     * @return The response from the repository.
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
            HttpStatus errorStatus = (HttpStatus) e.getStatusCode();
            String responseBody = e.getResponseBodyAsString();

            if (errorStatus == HttpStatus.NOT_FOUND) {
                ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, responseBody);
                response = new ResponseEntity<>(apiError.toString(), apiError.getStatus());
            } else if (errorStatus == HttpStatus.UNPROCESSABLE_ENTITY) {
                ApiError apiError = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, responseBody);
                response = new ResponseEntity<>(apiError.toString(), apiError.getStatus());
            } else {
                // Default für alle anderen Fehler
                ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, responseBody);
                response = new ResponseEntity<>(apiError.toString(), apiError.getStatus());
            }
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
        response = new ResponseEntity<>(convertObjectToJson(eventDTO), HttpStatus.OK);
        return response;
    }

    /**
     * Retrieves an event string by its ID.
     *
     * @param eventID The ID of the event to retrieve.
     * @return A string representation of the event.
     */
    public String getEventString(UUID eventID) {
        log.info("get event by eventID: {}", eventID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                return response.getBody().toString();
            }
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response.getBody().toString();
    }

    /**
     * Retrieves an event by its ID.
     *
     * @param eventID The ID of the event to retrieve.
     * @return The response from the repository.
     */
    public ResponseEntity<?> getEvent(UUID eventID) {
        log.info("get event by eventID: {}", eventID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                return response;
            }
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    /**
     * Updates an event.
     *
     * @param event The event to update.
     * @return The response from the repository.
     */
    public ResponseEntity<?> update(Event event) {
        log.info("update event: {}", event.getID());
        log.info("event Participants: {}", event.getParticipants());
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + event.getID();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> request = new HttpEntity<Event>(event, headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                return response;
            }
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    /**
     * Deletes an event.
     *
     * @param eventID The ID of the event to delete.
     * @return The response from the repository.
     */
    public ResponseEntity<?> delete(UUID eventID) {
        log.info("delete eventID: {}", eventID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
            if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
                return response;
            }
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    /**
     * Adds a user to an event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user.
     * @return The updated event.
     */
    public Event addUser(UUID eventID, UUID userID) {
        log.info("addUser: eventID={}, userID={}", eventID, userID);
        String eventString = getEventString(eventID);
        Event event = Event.eventFromJson(eventString).setID(eventID);

        if (event == null || !event.addParticipant(userID)) {
            throw new IllegalArgumentException("Failed to add user to event");
        }

        log.info("addUser, participants: {}", event.getParticipants());
        update(event);
        return event;
    }

    /**
     * Removes a user from an event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user.
     * @return The updated event.
     */
    public Event removeUser(UUID eventID, UUID userID) {
        log.info("removeUser: eventID={}, user={}", eventID, userID);
        String eventString = getEventString(eventID);
        Event event = Event.eventFromJson(eventString);

        if (event == null || !event.removeParticipant(userID)) {
            return null;
        }

        log.info("removeUser: participants{}", event.getParticipants());
        update(event);
        return event;
    }

    /**
     * Removes a user from all events.
     *
     * @param userID The ID of the user.
     */
    public void removeUser(UUID userID) {
        log.info("removeUser: userId={}", userID);
        for (Event event : Event.collectionFromJson(getAll())) {
            if (event.removeParticipant(userID)) {
                update(event);
            }
        }
    }

    /**
     * Adds a rating to an event.
     *
     * @param eventID The ID of the event.
     * @param userID  The ID of the user.
     * @param rating  The rating to add.
     * @return A string representation of the updated event.
     */
    public String addRating(UUID eventID, UUID userID, int rating) {
        log.info("addRating: eventID={}, userID={}, rating={}", eventID, userID, rating);
        String eventString = getEventString(eventID);
        Event event = Event.eventFromJson(eventString);
        if (event == null || !event.contains(userID)) {
            return null;
        }

        event.rate(userID, rating);
        update(event);
        return convertObjectToJson(event);
    }

    public double getRating(UUID eventID) {
        log.info("getRating: eventID={}", eventID);
        String eventString = getEventString(eventID);
        Event event = Event.eventFromJson(eventString);
        return event.rating();
    }

    // TODO dateTime richtig converten
    private String convertObjectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
