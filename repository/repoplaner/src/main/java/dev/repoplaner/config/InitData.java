package dev.repoplaner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.repoplaner.model.Event;
import dev.repoplaner.model.Geolocation;
import dev.repoplaner.model.User;
import dev.repoplaner.service.RepositoryService;
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

                log.debug("create user \"Yannis Koerner\"");
                User yannis = new User()
                                .setFirstName("Yannis")
                                .setLastName("Koerner")
                                .setEmail("yannis.koerner@stud.fra-uas.de")
                                .setPassword("123");

                repositoryService.putUser(yannis);

                log.debug("create user \"Tristan Buls\"");
                User tristan = new User()
                                .setFirstName("Tristan")
                                .setLastName("Buls")
                                .setEmail("tristan.buls@stud.fra-uas.de")
                                .setPassword("456");

                repositoryService.putUser(tristan);

                log.debug("create user \"Daniel Schor\"");
                User daniel = new User()
                                .setFirstName("Daniel")
                                .setLastName("Schor")
                                .setEmail("daniel.schor@stud.fra-uas.de")
                                .setPassword("789")
                                .setOrganizer(true);

                repositoryService.putUser(daniel);

                log.debug("create event \"Statistik Vorlesung\"");
                Event statistik = new Event()
                                .setName("Statistik Vorlesung")
                                .setDescription("Statistik Vorlesung am 25.01.2024 um 08:15 Uhr.")
                                .setDateTime(LocalDateTime.of(2024, 1, 25, 8, 15, 0));

                statistik.addParticipant(daniel.getID());
                statistik.addParticipant(tristan.getID());

                statistik.rate(daniel.getID(), 2);
                statistik.rate(tristan.getID(), 3);

                repositoryService.putEvent(statistik);

                log.debug("create event \"Webanwendung Vorlesung\"");
                Event webanwendung = new Event()
                                .setName("Webanwendung Vorlesung")
                                .setDescription("Webanwendung Vorlesung am 22.01.2024 um 16:00 Uhr.")
                                .setDateTime(LocalDateTime.of(2024, 1, 22, 16, 0, 0));

                webanwendung.addParticipant(yannis.getID());
                webanwendung.addParticipant(tristan.getID());
                webanwendung.addParticipant(daniel.getID());

                webanwendung.rate(yannis.getID(), 1);
                webanwendung.rate(tristan.getID(), 3);
                webanwendung.rate(daniel.getID(), 4);

                repositoryService.putEvent(webanwendung);

        }

}
