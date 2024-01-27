package dev.userplaner.model;

import java.util.UUID;

public class UserDTO {

    private final UUID userID;
    private String fullName;

    public UserDTO(User user) {
        this.userID = user.getID();
        this.fullName = user.getFirstName() + " " + user.getLastName();
    }

    public UserDTO(UUID userID, String firstName, String lastName, boolean organizer) {
        this.userID = userID;
        this.fullName = firstName + " " + lastName;
    }

    public UserDTO(UUID userID, String fullName, boolean organizer) {
        this.userID = userID;
        this.fullName = fullName;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}