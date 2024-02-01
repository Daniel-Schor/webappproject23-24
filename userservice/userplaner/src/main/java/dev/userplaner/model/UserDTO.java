package dev.userplaner.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * This class represents a Data Transfer Object (DTO) for a user.
 * It is used to transfer data between processes or components.
 * It includes fields for the user's ID, first name, last name, and organizer
 * status.
 */
public class UserDTO {

    UUID userID;
    String firstName, lastName;
    boolean organizer;

    /**
     * Default constructor for UserDTO.
     * Initializes the user ID to a random ID, the first name to "John", the last
     * name to "Doe", and the organizer status to false.
     */
    public UserDTO() {
        this.userID = UUID.randomUUID();
        this.firstName = "John";
        this.lastName = "Doe";
        this.organizer = false;
    }

    /**
     * Constructs a UserDTO from a User object.
     *
     * @param user The User object.
     */
    public UserDTO(User user) {
        this.userID = user.getID();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.organizer = user.isOrganizer();
    }

    /**
     * Constructs a UserDTO with the specified user ID, first name, last name, and
     * organizer status.
     *
     * @param userID    The unique identifier for the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param organizer The organizer status of the user.
     */
    public UserDTO(UUID userID, String firstName, String lastName, boolean organizer) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organizer = organizer;
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

    public boolean isOrganizer() {
        return organizer;
    }

    public void setOrganizer(boolean organizer) {
        this.organizer = organizer;
    }

    /**
     * Converts a JSON string into a collection of UserDTO objects.
     *
     * @param s The JSON string.
     * @return A collection of UserDTO objects.
     */
    public static Collection<UserDTO> collectionFromJsonUserDTO(String s) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Collection<UserDTO> values = new ArrayList<>();

        try {
            values = mapper.readValue(s, new TypeReference<Collection<UserDTO>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * Converts a JSON string into a UserDTO object.
     *
     * @param s The JSON string.
     * @return A UserDTO object.
     */
    public static UserDTO userFromJson(String s) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        UserDTO user = new UserDTO(new User());

        try {
            user = mapper.readValue(s, new TypeReference<UserDTO>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return user;
    }
}