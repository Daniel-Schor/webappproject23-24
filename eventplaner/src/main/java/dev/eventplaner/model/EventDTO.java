package dev.eventplaner.model;

import java.util.UUID;

/**
 * This class represents a Data Transfer Object (DTO) for an event.
 * It is used to transfer data between processes or components.
 */
public class EventDTO {

    private final UUID eventID;
    private String name;

    /**
     * Constructs an EventDTO from an Event object.
     *
     * @param event The Event object.
     */
    public EventDTO(Event event) {
        this.eventID = event.getID();
        this.name = event.getName();
    }

    /**
     * Constructs an EventDTO with the specified event ID and name.
     *
     * @param eventID The unique identifier for the event.
     * @param name    The name of the event.
     */
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
