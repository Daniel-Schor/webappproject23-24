package dev.eventcreator.model;

import java.util.UUID;

public class UserDTO {

    UUID userID;
    String fullName;
    boolean organizer; // Add this line

    public UserDTO(User user) {
        this.userID = user.getID();
        this.fullName = user.getFirstName() + " " + user.getLastName();
        this.organizer = user.isOrganizer(); // Add this line
    }

    public UserDTO(UUID userID, String firstName, String lastName, boolean organizer) {
        this.userID = userID;
        this.fullName = firstName + " " + lastName;
        this.organizer = organizer; // Add this line
    }

    public UserDTO(UUID userID, String fullName, boolean organizer) {
        this.userID = userID;
        this.fullName = fullName;
        this.organizer = organizer; // Add this line
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isOrganizer() { // Add this method
        return organizer;
    }

    public void setOrganizer(boolean organizer) { // Add this method
        this.organizer = organizer;
    }
}