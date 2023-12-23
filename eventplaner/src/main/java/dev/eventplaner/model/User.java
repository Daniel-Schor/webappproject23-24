package dev.eventplaner.model;

import dev.eventplaner.repository.UserRepository;

public class User {

    private Long userID = 0L;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean organizer;

    private static UserRepository userRepository = new UserRepository();

    public User() {
        userID++;
        firstName="null";
        lastName="null";
        email="null";
        password="null";
        organizer=false;
    }

    public User(String firstName, String lastName, String email, String password, boolean organizer) {
        userID++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.organizer = organizer;
    }

    public User(String firstName, String lastName, String email, String password) {
        userID++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        organizer=false;
    }

    public Long getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName!=null) {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName!=null) {
            this.lastName = lastName;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email!=null) {
            this.email = email;
        }
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        if(password!=null) {
            this.password = password;
        }
    }

    public Boolean isOrganizer(){
        return organizer;
    }

    public void setOrganizer(Boolean organizer) {
        this.organizer = organizer;
    }

}
