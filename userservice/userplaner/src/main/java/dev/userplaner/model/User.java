package dev.userplaner.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * User class represents a user in the system.
 * It contains information about the user such as their ID, name, email,
 * password, and whether they are an organizer.
 */
public class User {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @JsonProperty("id")
    private UUID userID;
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

    /**
     * Converts a JSON string into a collection of User objects.
     *
     * @param s the JSON string to be converted
     * @return a collection of User objects
     */
    public static Collection<User> collectionFromJson(String s) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Collection<User> values = new ArrayList<>();

        try {
            values = mapper.readValue(s, new TypeReference<Collection<User>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * Represents a user.
     */
    public static User userFromJson(String s) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User user = new User();

        try {
            user = mapper.readValue(s, new TypeReference<User>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return user;
    }

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

    public User setID(UUID userID) {
        this.userID = userID;
        return this;
    }

}
