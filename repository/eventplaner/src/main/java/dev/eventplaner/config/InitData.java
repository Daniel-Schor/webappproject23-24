package dev.eventplaner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.eventplaner.model.Event;
import dev.eventplaner.model.User;
import dev.eventplaner.service.RepositoryService;
import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;

@Component
public class InitData {

        private final Logger log = LoggerFactory.getLogger(InitData.class);

        @Autowired
        RepositoryService repositoryService;

        @PostConstruct
        public void init() {
                log.debug("### Initialize Data ###");

                log.debug("create event \"Statistik Vorlesung\"");
                repositoryService.putEvent(
                                new Event()
                                                .setName("Statistik Vorlesung")
                                                .setDescription("Statistik Vorlesung am 11.01.2024 um 08:15 Uhr.")
                                                .setDateTime(LocalDateTime.of(2024, 1, 11, 8, 15, 0)));

                log.debug("create event \"Webanwendung Vorlesung\"");
                repositoryService.putEvent(
                                new Event()
                                                .setName("Webanwendung Vorlesung")
                                                .setDescription("Webanwendung Vorlesung am 11.01.2024 um 08:15 Uhr.")
                                                .setDateTime(LocalDateTime.of(2024, 1, 8, 16, 0, 0)));

                log.debug("create user \"Yannis Koerner\"");
                repositoryService.putUser(
                                new User()
                                                .setFirstName("Yannis")
                                                .setLastName("Koerner")
                                                .setEmail("yannis.koerner@stud.fra-uas.de")
                                                .setPassword("123")
                                                .setOrganizer(true));

                log.debug("create user \"Tristan Buls\"");
                repositoryService.putUser(
                                new User()
                                                .setFirstName("Tristan")
                                                .setLastName("Buls")
                                                .setEmail("tristan.buls@stud.fra-uas.de")
                                                .setPassword("456"));

                log.debug("create user \"Daniel Schor\"");
                repositoryService.putUser(
                                new User()
                                                .setFirstName("Daniel")
                                                .setLastName("Schor")
                                                .setEmail("daniel.schor@stud.fra-uas.de")
                                                .setPassword("789"));

        }

}
