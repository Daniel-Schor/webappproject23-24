package dev.eventplaner.model;

import java.util.UUID;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class EventDTO {

    private final UUID eventID;
    private String name;

    public EventDTO(Event event) {
        this.eventID = event.getID();
        this.name = event.getName();
    }

    public EventDTO(UUID eventID, String name) {
        this.eventID = eventID;
        this.name = name;
    }

    public UUID getEventID() {
        return eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
