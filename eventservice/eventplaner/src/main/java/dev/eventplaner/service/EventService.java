package dev.eventplaner.service;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;

import dev.eventplaner.model.ApiError;
import dev.eventplaner.model.Event;
import dev.eventplaner.model.EventDTO;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    @Value("${repository.url}")
    private String apiUrl;

    public String create(Event event) {
        log.info("Event Created: {}, {}", event.getName(), event.getID());

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> request = new HttpEntity<Event>(event, headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response.getBody().toString();
    }

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

    public String getAllDTO() {
        log.info("get all Events as DTO");

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events";

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError.toString(), apiError.getStatus());
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Collection<Event> values = new ArrayList<>();

        try {
            values = mapper.readValue(response.getBody(), new TypeReference<Collection<Event>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Collection<EventDTO> eventDTO = new ArrayList<>();
        if (values != null) {
            for (Event event : values) {
                eventDTO.add(new EventDTO(event));
            }
        }
        return convertObjectToJson(eventDTO);
    }

    public String getEvent(UUID eventID) {
        log.info("get event by eventID: {}", eventID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

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

    public String update(Event event) {
        log.info("update event: {}", event.getID());
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + event.getID();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Event> request = new HttpEntity<Event>(event, headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response.getBody().toString();
    }

    public String delete(UUID eventID) {
        log.info("delete eventID: {}", eventID);

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/events/" + eventID;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<?> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response.getBody().toString();
    }

    public Event addUser(UUID eventID, UUID userID) {
        log.info("addUser: eventID={}, userID={}", eventID, userID);
        String eventString = getEvent(eventID);
        Event event = Event.fromString(eventString);

        if (event == null || !event.addParticipant(userID)) {
            throw new IllegalArgumentException("Failed to add user to event");
        }
        log.info("addUser: participants{}", event.getParticipants());
        update(event);
        return event;
    }

    public Event removeUser(UUID eventID, UUID userID) {
        log.info("removeUser: eventID={}, user={}", eventID, userID);
        String eventString = getEvent(eventID);
        Event event = Event.fromString(eventString);

        if (event == null || !event.removeParticipant(userID)) {
            throw new IllegalArgumentException("Failed to remove user from event");
        }
        log.info("removeUser: participants{}", event.getParticipants());
        update(event);
        return event;
    }

    public void removeUser(UUID userID) {
        log.info("removeUser: userId={}", userID);
        for (Event event : Event.collectionFromString(getAll())) {
            if (event.removeParticipant(userID)) {
                update(event);
            }
        }
    }

    public String addRating(UUID eventID, UUID userID, int rating) {
        log.info("addRating: eventID={}, userID={}, rating={}", eventID, userID, rating);
        String eventString = getEvent(eventID);
        Event event = Event.fromString(eventString);
        if (event == null) {
            throw new IllegalArgumentException("Failed to add rating to event");
        }
        event.rate(userID, rating);
        update(event);
        return eventString;
    }

    public double getRating(UUID eventID) {
        log.info("getRating: eventID={}", eventID);
        String eventString = getEvent(eventID);
        Event event = Event.fromString(eventString);
        return event.getRating();
    }

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
