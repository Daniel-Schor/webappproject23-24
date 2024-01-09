package dev.eventplaner.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {

    private final UUID eventID;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private Geolocation geolocation;
    private int maxParticipants;
    private Map<UUID, Integer> participants;
    private UUID organizerUserID;

    /**
     * Default constructor for the Event class.
     * Initializes the event with default values.
     */
    public Event() {
        this.eventID = UUID.randomUUID();
        this.name = "Default Event";
        this.description = "Default Description";
        this.dateTime = LocalDateTime.now();
        this.geolocation = new Geolocation(50.130444, 8.692556);// new Address("Nibelungenplatz", "1", "60318",
                                                                // "Frankfurt am Main", "Deutschland");
        this.maxParticipants = 10;
        this.participants = new HashMap<>();
        this.organizerUserID = null;
    }

    /**
     * Constructor for the Event class.
     * Initializes the event with the specified values.
     * 
     * @param name            The name of the event.
     * @param description     The description of the event.
     * @param dateTime        The date and time of the event.
     * @param location        The location of the event.
     * @param maxParticipants The maximum number of participants for the event.
     * @param organizerUserID The UUID of the organizer user.
     */
    public Event(String name, String description, LocalDateTime dateTime, Geolocation geolocation, int maxParticipants,
            UUID organizerUserID) {
        this.eventID = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.geolocation = geolocation;
        this.maxParticipants = maxParticipants;
        this.participants = new HashMap<>();
        this.organizerUserID = organizerUserID;
    }

    /**
     * Adds a participant to the event.
     * 
     * @param participant the participant to be added
     * @return true if the participant was added successfully, false if the
     *         participant limit is reached or participant is already in the event
     */
    public synchronized boolean addParticipant(UUID participantID) {
        if (participantID != null && participants.size() < maxParticipants
                || !participants.containsKey(participantID)) {
            this.participants.put(participantID, null);
            return true;
        }
        return false;
    }

    /**
     * Removes a participant from the event.
     * 
     * @param participantID the ID of the participant to be removed
     * @return true if the participant was removed successfully, false if the ID is not in the event
     */
    public synchronized boolean removeParticipant(UUID participantID) {
        if (participantID != null) {
            return (this.participants.remove(participantID) != null);
        } else {
            return false;
        }
    }

    /**
     * Returns the average rating of the event.
     *
     * @return The average rating of the event. If no rating is available, 0 is
     */
    public double getRating() {
        double rating = 0;
        int nullValues = 0;

        for (Integer i : participants.values()) {
            if (i == null) {
                nullValues++;
            } else {
                rating += i;
            }
        }
        return ((double) rating) / (participants.size() - nullValues);
    }

    /**
     * Adds a rating for the event by a user.
     * 
     * @param userID the ID of the user giving the rating
     * @param rating the rating value to be added
     * @return true if the rating was successfully added, false otherwise (rating
     *         out of bounds or user not in event)
     */
    public boolean rate(UUID userID, int rating) {
        if (userID == null || (rating < 0 || rating > 5)) {
            return false;
        }
        participants.put(userID, rating);
        return true;
    }

    public boolean contains(UUID userID) {
        return this.participants.containsKey(userID);
    }

    // -- GETTER AND SETTER --

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public UUID getID() {
        return this.eventID;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Geolocation getLocation() {
        return this.geolocation;
    }

    public int getMaxParticipants() {
        return this.maxParticipants;
    }

    public Map<UUID, Integer> getParticipants() {
        return Collections.unmodifiableMap(this.participants);
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public UUID getOrganizerUserID() {
        return this.organizerUserID;
    }

    public Event setName(String name) {
        if (name != null) {
            this.name = name;
        }
        return this;
    }

    public Event setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
        return this;
    }

    public Event setDateTime(LocalDateTime dateTime) {
        if (dateTime != null) {
            this.dateTime = dateTime;
        }
        return this;
    }

    public Event setMaxParticipants(int maxParticipants) {
        if (maxParticipants >= 0) {
            this.maxParticipants = maxParticipants;
        }
        return this;
    }

    public Event setOrganizerUserID(UUID organizerUserID) {
        if (organizerUserID != null) {
            this.organizerUserID = organizerUserID;
        }
        return this;
    }

    public Event setLocation(Geolocation geolocation) {
        if (geolocation != null) {
            this.geolocation = geolocation;
        }
        return this;
    }
}
