package dev.repoplaner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.repoplaner.model.Event;
import dev.repoplaner.model.User;
import dev.repoplaner.service.RepositoryService;
import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;

/**
                 * This class is responsible for initializing data in the application.
                 * It creates events and users using the RepositoryService.
                 */
@Component
public class InitData {

        /**
         * The logger used for logging messages in the InitData class.
         */
        private final Logger log = LoggerFactory.getLogger(InitData.class);

        /**
         * Autowired field for accessing the repository service.
         */
        @Autowired
        RepositoryService repositoryService;

        /**
         * Initializes the data for the application.
         * This method is annotated with @PostConstruct to indicate that it should be executed after the bean is constructed.
         * It creates events and users in the repository service.
         */
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
