package dev.eventplaner.model;

import java.util.UUID;

public class UserDTO {

    UUID userID;
    String fullName;

    public UserDTO(User user) {
        this.userID = user.getID();
        this.fullName = user.getFirstName() + " " + user.getLastName();
    }

    public UserDTO(UUID userID, String firstName, String lastName) {
        this.userID = userID;
        this.fullName = firstName + " " + lastName;
    }

    public UserDTO(UUID userID, String fullName) {
        this.userID = userID;
        this.fullName = fullName;
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

}
