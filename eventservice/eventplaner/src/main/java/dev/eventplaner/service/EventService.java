package dev.eventplaner.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.eventplaner.model.Event;
import dev.eventplaner.model.EventDTO;


@Service
public class EventService {
    

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    public Event create(Event event){
        log.info("Event Created: {}, {}", event.getName(), event.getID());
        eventRepository.put(event.getID(), event);
        return event;
    }

    public Collection<Event> getAll(){
        log.info("get all Events");
        return eventRepository.values();
    }

    public Collection<EventDTO> getAllDTO(){
        log.info("get all Events as DTO");
        Collection<EventDTO> eventsDTO = new ArrayList<>();
        for (Event event : eventRepository.values()) {
            eventsDTO.add(new EventDTO(event));
        }
        return eventsDTO;
    }

    public Event getEvent(UUID eventID) {
        log.info("get event by eventID: {}", eventID);
        return eventRepository.get(eventID);
    }

    public Event update(UUID eventID, Event event){
        log.info("update event: {}", event);
        eventRepository.put(eventID, event);
        return event;
    }

    public Event delete(UUID eventID){
        log.info("delete eventID: {}", eventID);
        return eventRepository.remove(eventID);
    }

    public Event addUser(UUID eventID, UUID userID) {
        log.info("addUser: eventID={}, userID={}", eventID, userID);
        Event event = eventRepository.get(eventID);
        if (event == null || !event.addParticipant(userID)) {
            throw new IllegalArgumentException("Failed to add user to event");
        }
        return event;
    }

    public Event removeUser(UUID eventID, UUID userID) {
        log.info("removeUser: eventID={}, user={}", eventID, userID);
        Event event = eventRepository.get(eventID);
        event.removeParticipant(userID);
        return event;
    }

    public void removeUser(UUID userID) {
        log.info("removeUser: userId={}", userID);
        for (Event event : eventRepository.values()) {
            event.removeParticipant(userID);
        }
    }

    public Event addRating(UUID eventID, UUID userID, int rating) {
        log.info("addRating: eventID={}, userID={}, rating={}", eventID, userID, rating);
        Event event = eventRepository.get(eventID);
        if (event == null) {
            throw new IllegalArgumentException("Failed to add rating to event");
        }
        event.rate(userID, rating);
        return event;
    }

    public double getRating(UUID eventID) {
        log.info("getRating: eventID={}", eventID);
        Event event = eventRepository.get(eventID);
        return event.getRating();
    }
}
