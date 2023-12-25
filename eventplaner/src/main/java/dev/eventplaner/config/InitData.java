package dev.eventplaner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.eventplaner.model.Event;
import dev.eventplaner.model.User;
import dev.eventplaner.service.EventService;
import jakarta.annotation.PostConstruct;

@Component
public class InitData {
    
    private final Logger log = LoggerFactory.getLogger(InitData.class);

    @Autowired
    EventService eventService;
    // TODO implement UserService
    //@Autowired
    //UserService userService;

    @PostConstruct
    public void init() {
        log.debug("### Initialize Data ###");

        log.debug("create event 1");
        Event event1 = eventService.create(new Event());
        log.debug("create event 2");
        Event event2 = eventService.create(new Event());

        // TODO with UserService
        log.debug("create user 1");
        User user1 = new User();
        log.debug("create user 2");
        User user2 = new User();
        log.debug("create user 3");
        User user3 = new User();

        log.debug("add user 1 to event 1");
        eventService.addUser(event1.getEventID(), user1);
        log.debug("add user 2 to event 1");
        eventService.addUser(event1.getEventID(), user2);

        log.debug("add user 3 to event 2");
        eventService.addUser(event2.getEventID(), user3);
        log.debug("add user 1 to event 2");
        eventService.addUser(event2.getEventID(), user1);

        log.debug("Rating added from user 1 in event 1. Rating: 5");
        eventService.addRating(event1.getEventID(), user1.getUserID(), 5);
        log.debug("Rating added from user 2 in event 1. Rating: 3");
        eventService.addRating(event1.getEventID(), user2.getUserID(), 3);


        log.debug("Rating added from user 3 in event 2. Rating: 2");
        eventService.addRating(event2.getEventID(), user3.getUserID(), 2);
        log.debug("Rating added from user 1 in event 2. Rating: 1");
        eventService.addRating(event2.getEventID(), user1.getUserID(), 1);

        log.debug("### Data initialized ###");
    }

}
