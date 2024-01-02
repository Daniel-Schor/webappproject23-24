package dev.eventplaner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.eventplaner.model.Event;
import dev.eventplaner.model.User;
import dev.eventplaner.service.EventService;
import dev.eventplaner.service.UserService;
import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;

@Component
public class InitData {

    private final Logger log = LoggerFactory.getLogger(InitData.class);

    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        log.debug("### Initialize Data ###");

        log.debug("create event \"Statistik Vorlesung\"");
        Event event1 = eventService.create(
                new Event()
                        .setName("Statistik Vorlesung")
                        .setDescription("Statistik Vorlesung am 11.01.2024 um 08:15 Uhr.")
                        .setDateTime(LocalDateTime.of(2024, 1, 11, 8, 15, 0)));

        log.debug("create event \"Webanwendung Vorlesung\"");
        Event event2 = eventService.create(
                new Event()
                        .setName("Webanwendung Vorlesung")
                        .setDescription("Webanwendung Vorlesung am 11.01.2024 um 08:15 Uhr.")
                        .setDateTime(LocalDateTime.of(2024, 1, 8, 16, 0, 0)));

        log.debug("create user \"Yannis Koerner\"");
        User user1 = userService.create(
                new User()
                        .setFirstName("Yannis")
                        .setLastName("Koerner")
                        .setEmail("yannis.koerner@stud.fra-uas.de")
                        .setPassword("123")
                        .setOrganizer(true));

        log.debug("create user \"Tristan Buls\"");
        User user2 = userService.create(
                new User()
                        .setFirstName("Tristan")
                        .setLastName("Buls")
                        .setEmail("tristan.buls@stud.fra-uas.de")
                        .setPassword("456"));

        log.debug("create user \"Daniel Schor\"");
        User user3 = userService.create(
                new User()
                        .setFirstName("Daniel")
                        .setLastName("Schor")
                        .setEmail("daniel.schor@stud.fra-uas.de")
                        .setPassword("789"));

                        
        log.debug("add user 1 to event 1");
        eventService.addUser(event1.getID(), user1.getID());
        log.debug("add user 2 to event 1");
        eventService.addUser(event1.getID(), user2.getID());

        log.debug("add user 3 to event 2");
        eventService.addUser(event2.getID(), user3.getID());
        log.debug("add user 1 to event 2");
        eventService.addUser(event2.getID(), user1.getID());

        log.debug("Rating added from user 1 in event 1. Rating: 5");
        eventService.addRating(event1.getID(), user1.getID(), 5);
        log.debug("Rating added from user 2 in event 1. Rating: 3");
        eventService.addRating(event1.getID(), user2.getID(), 3);

        log.debug("Rating added from user 3 in event 2. Rating: 2");
        eventService.addRating(event2.getID(), user3.getID(), 2);
        log.debug("Rating added from user 1 in event 2. Rating: 1");
        eventService.addRating(event2.getID(), user1.getID(), 1);

        log.debug("### Data initialized ###");
    }

}
