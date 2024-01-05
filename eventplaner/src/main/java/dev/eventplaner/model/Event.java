package dev.eventplaner.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {

    private final UUID eventID;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private Location location;
    private int maxParticipants;
    private Set<UUID> participants;
    private UUID organizerUserID;
    private Map<UUID, Integer> ratings;

    /**
     * Default constructor for the Event class.
     * Initializes the event with default values.
     */
    public Event() {
        this.eventID = UUID.randomUUID();
        this.name = "Default Event";
        this.description = "Default Description";
        this.dateTime = LocalDateTime.now();
        this.location = new Address("Nibelungenplatz", "1", "60318", "Frankfurt am Main", "Deutschland");
        this.maxParticipants = 10;
        this.participants = new HashSet<>();
        this.organizerUserID = null;
        this.ratings = new HashMap<>();
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
    public Event(String name, String description, LocalDateTime dateTime, Location location, int maxParticipants,
            UUID organizerUserID) {
        this.eventID = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.participants = new HashSet<>();
        this.organizerUserID = organizerUserID;
        this.ratings = new HashMap<>();
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
     * @param participants    The list of participant UUIDs for the event.
     * @param organizerUserID The UUID of the organizer user.
     * @param rating          The rating of the event.
     * @param ratings    The list of rated user UUIDs for the event.
     */
    public Event(String name, String description, LocalDateTime dateTime, Location location, int maxParticipants, HashSet<UUID> participants, UUID organizerUserID, int rating, HashMap<UUID, Integer> ratings) {
        this.eventID = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.participants = participants;
        this.organizerUserID = organizerUserID;
        this.ratings = ratings;
    }

    /**
        * Adds a participant to the event.
        * 
        * @param participant the participant to be added
        * @return true if the participant was added successfully, false if the participant limit is reached or participant is already in the event
        */
    public synchronized boolean addParticipant(UUID participantID) {
        if (participantID != null && participants.size() < maxParticipants) {
            return this.participants.add(participantID);
        }
        return false;
    }

    /**
     * Removes a participant from the event.
     * 
     * @param participantID the ID of the participant to be removed
     */
    public synchronized void removeParticipant(UUID participantID) {
        if (participantID != null) {
            this.participants.remove(participantID);
        }
    }

    /**
     * Returns the average rating of the event.
     *
     * @return The average rating of the event. If no rating is available, 0 is 
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public double getRating() {
        double rating = 0;
        for (Integer i : ratings.values()) {
            rating += i;
        }
        return ((double) rating) / ratings.size();
    }

    /**
     * Adds a rating for the event by a user.
     * 
     * @param userID the ID of the user giving the rating
     * @param rating the rating value to be added
     * @return true if the rating was successfully added, false otherwise (rating out of bounds or user not in event)
     */
    public boolean rate(UUID userID, int rating) {
        if (userID == null || !participants.contains(userID) || (rating < 0 || rating > 5)) {
            return false;
        }
        this.ratings.put(userID, rating);
        return true;
    }

    public boolean contains(UUID userID) {
        return this.participants.contains(userID);
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
    public Location getLocation() {
        return this.location;
    }

    public int getMaxParticipants() {
        return this.maxParticipants;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Set<UUID> getParticipants() {
        return Collections.unmodifiableSet(this.participants);
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public UUID getOrganizerUserID() {
        return this.organizerUserID;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Map<UUID, Integer> getratings() {
        return Collections.unmodifiableMap(this.ratings);
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

    public Event setLocation(Location location) {
        if (location != null) {
            this.location = location;
        }
        return this;
    }
}
