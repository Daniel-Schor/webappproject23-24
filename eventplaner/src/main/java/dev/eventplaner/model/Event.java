package dev.eventplaner.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Event {

    private UUID eventID;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private Location location;
    private int maxParticipants;
    private Set<UUID> participants;
    private UUID organizerUserID;
    private int rating;
    private Set<UUID> ratedUserIDs;

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
        this.maxParticipants = 0;
        this.participants = new HashSet<>();
        this.organizerUserID = null;
        this.rating = 0;
        this.ratedUserIDs = new HashSet<>();
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
        this.rating = 0;
        this.ratedUserIDs = new HashSet<>();
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
     * @param ratedUserIDs    The list of rated user UUIDs for the event.
     */
    public Event(String name, String description, LocalDateTime dateTime, Location location, int maxParticipants,
            HashSet<UUID> participants, UUID organizerUserID, int rating, HashSet<UUID> ratedUserIDs) {
        this.eventID = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.maxParticipants = maxParticipants;
        this.participants = participants;
        this.organizerUserID = organizerUserID;
        this.rating = rating;
        this.ratedUserIDs = ratedUserIDs;
    }

    /**
        * Adds a participant to the event.
        * 
        * @param participantID the ID of the participant to be added
        * @return true if the participant was added successfully, false if the participant limit is reached
        */
    public synchronized boolean addParticipant(UUID participantID) {
        if (participantID != null && participants.size() < maxParticipants) {
            this.participants.add(participantID);
            return true;
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
     * @see #getRating()
     */
    public double getAvgRating() {
        if (this.ratedUserIDs.size() == 0) {
            return 0;
        }
        return ((double) this.rating) / this.ratedUserIDs.size();
    }

    /**
     * Adds a rating for the event by a user.
     * 
     * @param userID the ID of the user giving the rating
     * @param rating the rating value to be added
     * @return true if the rating was successfully added, false otherwise (user
     *         already rated)
     */
    public synchronized boolean addRating(UUID userID, int rating) {
        if (!(userID != null && !this.ratedUserIDs.contains(userID))) {
            return false;
        }
        this.rating += rating;
        this.ratedUserIDs.add(userID);
        return true;
    }

    // -- GETTER AND SETTER --

    public UUID getEventID() {
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

    public Location getLocation() {
        return this.location;
    }

    public int getMaxParticipants() {
        return this.maxParticipants;
    }

    public Set<UUID> getParticipants() {
        return Collections.unmodifiableSet(this.participants);
    }

    public UUID getOrganizerUserID() {
        return this.organizerUserID;
    }

    public Set<UUID> getRatedUserIDs() {
        return Collections.unmodifiableSet(this.ratedUserIDs);
    }

    /**
     * @return the raw rating of the event
     * @see #getAvgRating()
     */
    public double getRating() {
        return this.rating;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime != null) {
            this.dateTime = dateTime;
        }
    }

    public void setMaxParticipants(int maxParticipants) {
        if (maxParticipants >= 0) {
            this.maxParticipants = maxParticipants;
        }
    }

    public void setOrganizerUserID(UUID organizerUserID) {
        if (organizerUserID != null) {
            this.organizerUserID = organizerUserID;
        }
    }

    public void setLocation(Location location) {
        if (location != null) {
            this.location = location;
        }
    }
}
