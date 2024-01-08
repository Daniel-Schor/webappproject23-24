package dev.eventplaner.service;

import dev.eventplaner.model.User;
import dev.eventplaner.model.UserDTO;
import dev.eventplaner.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class UserService {

    // Logger instance for this class, used to log system messages, warnings, and
    // errors.
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
     // Read the URL of the external API from properties file.
    @Value("${userservice.url}")
    String apiUrl;

    // Autowired annotation is used to automatically inject the UserRepository
    // instance into this class.
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a user by their ID.
     *
     * @param userID The ID of the user to retrieve.
     * @return The User object corresponding to the given ID, or null if no such
     *         user exists.
     */
    public ResponseEntity<?> getUser(UUID userID) {
        log.info("get user by userID: {}", userID);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + userID;
        

        HttpHeader headers = new HttpHeaders();
        return response;
    }

    /**
     * Creates a new user.
     *
     * @param user The User object to create.
     */
    public User create(User user) {
        log.info("User Created: {}", user.getID());
        userRepository.put(user.getID(), user);
        return user;
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userID The ID of the user to delete.
     */
    public User delete(UUID userID) {
        if (userRepository.get(userID) == null) {
            log.warn("User with ID {} not found", userID);
        }
        if (userRepository.get(userID) != null) {
            log.warn("User with ID {} found", userID);
            log.info("User Deleted: {}", userID);
            return userRepository.remove(userID);
        }
        return null;

    }

    /**
     * Updates a user.
     *
     * @param user The User object to update.
     */
    public User update(User user) {
        log.info("User Updated: {}", user.getID());
        userRepository.put(user.getID(), user);
        return user;
    }

    /**
     * Retrieves all users.
     *
     * @return An Collection of all User objects.
     */
    public Collection<User> getAll() {
        log.info("getAllUsers");
        return userRepository.values();
    }

    public Collection<UserDTO> getAllDTO(){
        log.info("get all Events as DTO");
        Collection<UserDTO> usersDTO = new ArrayList<>();
        for (User user : userRepository.values()) {
            usersDTO.add(new UserDTO(user));
        }
        return usersDTO;
    }

}
