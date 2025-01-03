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

public class User {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @JsonProperty("id")
    private UUID userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean organizer;

    public User() {
        this.userID = UUID.randomUUID();
        this.firstName = null;
        this.lastName = null;
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

    public User setPassword(String password) {
        if (password != null) {
            this.password = encoder.encode(password);
        }
        return this;
    }

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
        Collection<User> values = new ArrayList<>();

        try {
            values = mapper.readValue(s, new TypeReference<Collection<User>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return values;
    }

    public static User userFromJson(String s) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        User user = new User();

        try {
            user = mapper.readValue(s, new TypeReference<User>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static String isValid(User user) {
        String detail = null;
        if (user.getFirstName() == null) {
            detail = "Event datetime must not be null";
        } else if (user.getFirstName().length() < 1 || user.getFirstName().length() > 30) {
            detail = "User firstname must be between 1 and 30 characters long";
        } else if (user.getLastName() == null) {
            detail = "User lastname must not be null";
        } else if (user.getLastName().length() < 1 || user.getLastName().length() > 30) {
            detail = "User lastname must be between 1 and 30 characters long";
        } else if (user.getEmail() == null) {
            detail = "User email must not be null";
        } else if (user.getEmail().length() < 5 || user.getEmail().length() > 50) {
            detail = "User email must be between 5 and 50 characters long";
        }
        return detail;
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
