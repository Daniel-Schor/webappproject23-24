package dev.repoplaner.service;

import java.util.Collection;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.repoplaner.model.Event;
import dev.repoplaner.model.User;
import dev.repoplaner.repository.EventRepository;
import dev.repoplaner.repository.UserRepository;


/**
 * This class represents a service for managing repositories.
 * It provides methods for creating, retrieving, updating, and deleting events and users.
 */
@Service
public class RepositoryService {
    

    private static final Logger log = LoggerFactory.getLogger(RepositoryService.class);

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;

    public Event putEvent(Event event){
        log.info("Event Created: {}, {}", event.getName(), event.getID());
        eventRepository.put(event.getID(), event);
        return event;
    }

    public Collection<Event> getAllEvents(){
        log.info("get all Events");
        return eventRepository.values();
    }

    public Event getEvent(UUID eventID) {
        log.info("get event by eventID: {}", eventID);
        return eventRepository.get(eventID);
    }

    public Event deleteEvent(UUID eventID){
        log.info("delete eventID: {}", eventID);
        return eventRepository.remove(eventID);
    }

    public User putUser(User user){
        log.info("User Created: {}, {}", user.getLastName(), user.getID());
        userRepository.put(user.getID(), user);
        return user;
    }

    public Collection<User> getAllUsers(){
        log.info("get all Users");
        return userRepository.values();
    }

    public User getUser(UUID userID) {
        log.info("get User by UserID: {}", userID);
        return userRepository.get(userID);
    }

    public User deleteUser(UUID userID){
        log.info("delete UserID: {}", userID);
        return userRepository.remove(userID);
    }
}
