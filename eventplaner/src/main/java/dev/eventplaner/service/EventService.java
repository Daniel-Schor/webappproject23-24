package dev.eventplaner.service;

import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.eventplaner.model.Event;
import dev.eventplaner.repository.EventRepository;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    /**
        * Creates a new event and adds it to the event repository.
        * 
        * @param event the event to be created
        * @return the created event
        */
    public Event create(Event event){
        log.info("Event Created: {}, {}", event.getName(), event.getID());
        eventRepository.put(event.getID(), event);
        return event;
    }

    /**
     * Retrieves all events.
     *
     * @return An Collection containing all events.
     */
    public Collection<Event> getAll(){
        log.info("getAllRooms");
        return eventRepository.values();
    }

    /**
     * Retrieves an event based on its ID.
     *
     * @param eventID The ID of the event to retrieve.
     * @return The event with the specified ID, or null if no event is found.
     */
    public Event getEvent(UUID eventID) {
        log.info("get event by eventID: {}", eventID);
        return eventRepository.get(eventID);
    }

    /**
     * Updates an event in the event repository.
     *
     * @param event The event to be updated.
     * @return The updated event.
     */
    public Event update(Event event){
        log.info("update event: {}", event);
        eventRepository.put(event.getID(), event);
        return event;
    }

    /**
     * Deletes an event with the specified event ID.
     *
     * @param eventID the ID of the event to be deleted
     * @return the deleted event
     */
    public Event delete(UUID eventID){
        log.info("delete eventID: {}", eventID);
        return eventRepository.remove(eventID);
    }

    /**
        * Adds a user to an event.
        *
        * @param eventID the ID of the event
        * @param user the user to be added
        * @return the updated event after adding the user
        * @throws IllegalArgumentException if the event is not found or failed to add the user
        */
    public Event addUser(UUID eventID, UUID userID) {
        log.info("addUser: eventID={}, userID={}", eventID, userID);
        Event event = eventRepository.get(eventID);
        if (event == null || !event.addParticipant(userID)) {
            throw new IllegalArgumentException("Failed to add user to event");
        }
        return event;
    }

    /**
     * Removes a user from an event.
     *
     * @param eventID the ID of the event
     * @param userID the ID of the user to be removed
     * @return the updated event after removing the user
     * @see #removeUser(UUID userID)
     */
    public Event removeUser(UUID eventID, UUID userID) {
        log.info("removeUser: eventID={}, user={}", eventID, userID);
        Event event = eventRepository.get(eventID);
        event.removeParticipant(userID);
        return event;
    }

    /**
     * Removes a user from all events.
     *
     * @param userID the ID of the user to be removed
     * @see #removeUser(UUID eventID, UUID userID)
     */
    public void removeUser(UUID userID) {
        log.info("removeUser: userId={}", userID);
        for (Event event : eventRepository.values()) {
            event.removeParticipant(userID);
        }
    }

    /**
    * Adds a rating for a user to a specific event.
    *
    * @param eventID The ID of the event to which the rating should be added.
    * @param userID The ID of the user who is giving the rating.
    * @param rating The rating to be added. This should be an integer.
    * @return The updated Event object with the new rating added, or null if the operation failed.
    * @throws IllegalArgumentException if the eventID or userID does not exist, or if the rating is not a valid integer.
    */
    public Event addRating(UUID eventID, UUID userID, int rating) {
        log.info("addRating: eventID={}, userID={}, rating={}", eventID, userID, rating);
        Event event = eventRepository.get(eventID);
        if (event == null || !event.addRating(userID, rating)) {
            throw new IllegalArgumentException("Failed to add rating to event");
        }
        return event;
    }

    /**
     * Retrieves the rating of an event based on its ID.
     *
     * @param eventID The ID of the event.
     * @return The average rating of the event.
     */
    public double getRating(UUID eventID) {
        log.info("getRating: eventID={}", eventID);
        Event event = eventRepository.get(eventID);
        return event.getAvgRating();
    }

}
