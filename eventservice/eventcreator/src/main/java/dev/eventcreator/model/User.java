package dev.eventcreator.model;

import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User class represents a user in the system.
 * It contains information about the user such as their ID, name, email,
 * password, and whether they are an organizer.
 */
public class User {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UUID userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean organizer;

    /**
     * Default constructor for the User class.
     * Initializes the user with default values.
     */
    public User() {
        this.userID = UUID.randomUUID();
        this.firstName = "John";
        this.lastName = "Doe";
        this.email = null;
        this.password = null;
        this.organizer = false;
    }

    /**
     * Constructor for the User class.
     * Initializes the user with the specified values.
     *
     * @param firstName The first name of the user
     * @param lastName  The last name of the user
     * @param email     The email of the user
     * @param password  The password of the user
     * @param organizer Whether the user is an organizer
     */
    public User(String firstName, String lastName, String email, String password, boolean organizer) {
        this.userID = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.setPassword(password);
        this.organizer = organizer;
    }

    /**
     * Constructor for the User class.
     * Initializes the user with the specified values.
     *
     * @param firstName The first name of the user
     * @param lastName  The last name of the user
     * @param email     The email of the user
     * @param password  The password of the user
     */

    public User(String firstName, String lastName, String email, String password) {
        this(firstName, lastName, email, password, false);
    }

    /**
     * Sets the user's password.
     * If the provided password is not null, it is encrypted and stored in the user
     * object's password field.
     * If the provided password is null, the password field remains unchanged.
     *
     * @param password The password to be set
     */

    public User setPassword(String password) {
        if (password != null) {
            this.password = encoder.encode(password);
        }
        return this;
    }

    /**
     * Checks if the provided password matches the user's password.
     *
     * @param password The password to be checked
     * @return True if the provided password matches the user's password, false
     *         otherwise
     */

    public boolean checkPassword(String password) {
        return encoder.matches(password, this.password);
    }

    //@Override
    //public String toString() {
    //    String s = "User{" + "userID=" + userID + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
    //            + ", email='" + email + '\'' + '\'' + ", organizer=" + organizer + '}';
    //    return s;
    //}

    // -- GETTER AND SETTER --
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public UUID getID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        if (firstName != null) {
            this.firstName = firstName;
        }
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName;
        }
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        if (email != null) {
            this.email = email;
        }
        return this;
    }

    public boolean isOrganizer() {
        return organizer;
    }

    public User setOrganizer(boolean organizer) {
        this.organizer = organizer;
        return this;
    }

}