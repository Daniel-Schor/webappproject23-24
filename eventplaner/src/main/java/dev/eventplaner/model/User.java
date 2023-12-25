package dev.eventplaner.model;

import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UUID userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean organizer;

    public User() {
        this.userID = UUID.randomUUID();
        this.firstName = "John";
        this.lastName = "Doe";
        this.email = null;
        this.password = null;
        this.organizer = false;
    }

    public User(String firstName, String lastName, String email, String password, boolean organizer) {
        this.userID = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.setPassword(password);
        this.organizer = organizer;
    }

    public User(String firstName, String lastName, String email, String password) {
        this(firstName, lastName, email, password, false);
    }

    public UUID getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null) {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        }
    }

    /* 
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null) {
            this.password = password;
        }
    }
    */

    public void setPassword(String password) {
        this.password = encoder.encode(password);
    }

    public Boolean checkPassword(String password) {
        return encoder.matches(password, this.password);
    }

    public Boolean isOrganizer() {
        return organizer;
    }

    public void setOrganizer(Boolean organizer) {
        this.organizer = organizer;
    }

}
