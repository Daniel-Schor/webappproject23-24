package dev.eventplaner.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UserDTO {


    @JsonProperty("ID")
    UUID userID;
    String firstName, lastName;
    boolean organizer; // Add this line

    public UserDTO(User user) {
        this.userID = user.getID();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.organizer = user.isOrganizer(); // Add this line
    }

    public UserDTO(UUID userID, String firstName, String lastName, boolean organizer) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organizer = organizer; // Add this line
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public boolean isOrganizer() { // Add this method
        return organizer;
    }

    public void setOrganizer(boolean organizer) { // Add this method
        this.organizer = organizer;
    }

     public static Collection<UserDTO> collectionFromJsonUser(String s) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Collection<UserDTO> values = new ArrayList<>();

        try {
            values = mapper.readValue(s, new TypeReference<Collection<UserDTO>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return values;
    }
}