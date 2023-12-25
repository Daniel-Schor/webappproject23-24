package dev.eventplaner.service;

import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.eventplaner.model.Event;
import dev.eventplaner.model.User;
import dev.eventplaner.repository.EventRepository;
import dev.eventplaner.repository.UserRepository;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private EventRepository eventRepository;

    // TODO finish this
    /**
        * Creates a new Event with the given name.
        *
        * @param name the name of the event
        * @return the created Event object
        */
    public Event create(String name){
        Event event = new Event();
        log.info("Event Created: {}, {}", name, event.getEventID());
        event.setName(name);
        eventRepository.put(event.getEventID(), event);
        return event;
    }

    /**
     * Retrieves all events.
     *
     * @return An Iterable containing all events.
     */
    public Iterable<Event> getAll(){
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
        eventRepository.put(event.getEventID(), event);
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
    public Event addUser(UUID eventID, User user) {
        log.info("addUser: eventID={}, userID={}", eventID, user.getUserID());
        Event event = eventRepository.get(eventID);
        if (event == null || !event.addParticipant(user)) {
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
     * Retrieves the user with the specified ID from the event's participants.
     *
     * @param eventID the ID of the event
     * @param userID the ID of the user
     * @return the user with the specified ID
     * @see #getUser(UUID userID)
     */
    public User getUser(UUID eventID, UUID userID) {
        log.info("getUser: eventID={}, userID={}", eventID, userID);
        Event event = eventRepository.get(eventID);
        return event.getParticipants().get(userID);
    }

    /**
     * Retrieves the user with the specified userID. Searches in all events.
     *
     * @param userID the unique identifier of the user
     * @return the user with the specified userID, or null if not found
     * @see #getUser(UUID eventID, UUID userID)
     */
    public User getUser(UUID userID) {
        log.info("getUser: userId={}", userID);
        for (Event event : eventRepository.values()) {
            if (event.getParticipants().get(userID) != null) {
                return event.getParticipants().get(userID);
            }
        }
        return null;
    }

    /**
     * Retrieves the UserRepository associated with the specified event ID.
     *
     * @param eventID the ID of the event
     * @return the UserRepository associated with the event
     * @see #getParticipantValues(UUID eventID)
     * @see #getParticipantIDs(UUID eventID)
     */
    public UserRepository getUserRepository(UUID eventID) {
        log.info("getUsers: eventID={}", eventID);
        Event event = eventRepository.get(eventID);
        return event.getParticipants();
    }

    /**
     * Retrieves the set of participant IDs for a given event.
     *
     * @param eventID the ID of the event
     * @return a set of participant IDs
     * @see #getUserRepository(UUID eventID)
     * @see #getParticipantValues(UUID eventID)
     */
    public Set<UUID> getParticipantIDs(UUID eventID) {
        log.info("getUsers: eventID={}", eventID);
        Event event = eventRepository.get(eventID);
        return event.getParticipants().keySet();
    }

    /**
     * Retrieves the participants of an event.
     *
     * @param eventID the ID of the event
     * @return an iterable collection of Users representing the participants of the event
     * @see #getUserRepository(UUID eventID)
     * @see #getParticipantIDs(UUID eventID)
     */
    public Iterable<User> getParticipantValues(UUID eventID) {
        log.info("getUsers: eventID={}", eventID);
        Event event = eventRepository.get(eventID);
        return event.getParticipants().values();
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
