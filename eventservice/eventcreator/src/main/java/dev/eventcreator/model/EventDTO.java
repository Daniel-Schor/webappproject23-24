package dev.eventcreator.model;

import java.util.UUID;

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

    public UUID getID() {
        return eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
