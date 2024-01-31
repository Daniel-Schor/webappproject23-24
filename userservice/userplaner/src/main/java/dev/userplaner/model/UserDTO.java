package dev.userplaner.model;

import java.util.UUID;

public class UserDTO {

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
}