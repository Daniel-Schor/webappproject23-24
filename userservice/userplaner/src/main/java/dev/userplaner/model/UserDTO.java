package dev.userplaner.model;

import java.util.UUID;

/**
 * Represents a Data Transfer Object (DTO) for a user.
 * A UserDTO object contains information about a user, such as their ID, first name, last name, and organizer status.
 */
public class UserDTO {

    UUID userID;
    String firstName, lastName;
    boolean organizer; // Add this line

    /**
     * Represents a Data Transfer Object (DTO) for a User.
     * This class is used to transfer user data between different layers of the application.
     */
    public UserDTO(User user) {
        this.userID = user.getID();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.organizer = user.isOrganizer(); // Add this line
    }


    /**
     * Constructs a new UserDTO object with the specified parameters.
     *
     * @param userID     the unique identifier of the user
     * @param firstName  the first name of the user
     * @param lastName   the last name of the user
     * @param organizer  indicates if the user is an organizer
     */    
    public UserDTO(UUID userID, String firstName, String lastName, boolean organizer) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organizer = organizer; // Add this line
    }

    /**
     * Returns the ID of the user.
     * @return The ID of the user.
     */
    public UUID getUserID() {
        return userID;
    }

    /**
     * Sets the ID of the user.
     * @param userID The ID of the user.
     */
    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    /**
     * Returns the first name of the user.
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Returns the last name of the user.
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the first name of the user.
     * @param firstName The first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName The last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the organizer status of the user.
     * @return The organizer status of the user.
     */
    public boolean isOrganizer() { // Add this method
        return organizer;
    }

    /**
     * Sets the organizer status of the user.
     * @param organizer The organizer status of the user.
     */
    public void setOrganizer(boolean organizer) { // Add this method
        this.organizer = organizer;
    }
}