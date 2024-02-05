package dev.eventcreator.model;

import java.util.UUID;

public class EventDTO {

    private final UUID eventID;
    private String name;

    /**
     * Constructs an EventDTO from an Event object.
     *
     * @param event The Event object to create the DTO from.
     */
    public EventDTO(Event event) {
        this.eventID = event.getID();
        this.name = event.getName();
    }

    /**
     * Constructs an EventDTO with the specified ID and name.
     *
     * @param eventID The ID of the event.
     * @param name    The name of the event.
     */
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
