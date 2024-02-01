package dev.eventcreator.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Event {

    @JsonProperty("id")
    private UUID eventID;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    @JsonProperty("location")
    private Geolocation geolocation;
    private int maxParticipants;
    private Map<UUID, Integer> participants;
    private UUID organizerUserID;

    /**
     * Default constructor for the Event class. New Address("Nibelungenplatz", "1",
     * "60318", "Frankfurt am Main", "Deutschland");
     * Initializes the event with default values.
     */
    public Event() {
        this.eventID = UUID.randomUUID();
        this.name = "Default Event";
        this.description = "Default Description";
        this.dateTime = LocalDateTime.now();
        this.geolocation = new Geolocation(50.130444, 8.692556);
        this.maxParticipants = 10;
        this.participants = new HashMap<>();
        this.organizerUserID = null;
    }

    /**
     * Default constructor for the Event class. New Address("Nibelungenplatz", "1",
     * "60318", "Frankfurt am Main", "Deutschland");
     * Initializes the event with default values.
     */
    public Event(UUID eventID) {
        this.eventID = eventID;
        this.name = "Default Event";
        this.description = "Default Description";
        this.dateTime = LocalDateTime.now();
        this.geolocation = new Geolocation(50.130444, 8.692556);
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
     * @return true if the participant was removed successfully, false if the ID is
     *         not in the event
     */
    public synchronized boolean removeParticipant(UUID participantID) {
        if (participantID == null) {
            return false;
        }
        if (!participants.containsKey(participantID)) {
            return false;
        }
        participants.remove(participantID);
        return true;
    }

    /**
     * Returns the average rating of the event.
     *
     * @return The average rating of the event. If no rating is available, 0 is
     */
    public double rating() {
        double rating = 0;
        int nullValues = 0;

        for (Integer i : participants.values()) {
            if (i == null) {
                nullValues++;
            } else {
                rating += i;
            }
        }
        if (participants.size() == nullValues) {
            return 0;
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

    public static Collection<Event> collectionFromJson(String s) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Collection<Event> values = new ArrayList<>();

        try {
            values = mapper.readValue(s, new TypeReference<Collection<Event>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return values;
    }

    public static Event eventFromJson(String s) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Event event = new Event();

        try {
            event = mapper.readValue(s, new TypeReference<Event>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return event;
    }

    // TODO add javadoc
    public static String isValid(Event event) {
        String detail = null;
        if (event.getDateTime() == null) {
            detail = "Event datetime must not be null";
        } else if (event.getName() == null) {
            detail = "Event name must not be null";
        } else if (event.getName().length() < 1 || event.getName().length() > 30) {
            detail = "Event name must be between 1 and 30 characters";
        } else if (event.getDescription() == null) {
            detail = "Event description must not be null";
        } else if (event.getDescription().length() < 1 || event.getDescription().length() > 1000) {
            detail = "Event description must be between 1 and 1000 characters";
        } else if (event.getLocation() == null) {
            detail = "Event location must not be null";
        } else if (event.getMaxParticipants() <= 0) {
            detail = "Event maxParticipants must be greater than 0";
        }
        return detail;
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

    public Event setID(UUID eventID) {
        this.eventID = eventID;
        return this;
    }

    @Override
    public String toString() {
        String s = "";
        s += "EventID: " + this.eventID + "\n";
        s += "Name: " + this.name + "\n";
        s += "Description: " + this.description + "\n";
        s += "DateTime: " + this.dateTime + "\n";
        s += "Location: " + this.geolocation + "\n";
        s += "MaxParticipants: " + this.maxParticipants + "\n";
        s += "Participants: " + this.participants + "\n";
        s += "OrganizerUserID: " + this.organizerUserID + "\n";
        return s;
    }
}
