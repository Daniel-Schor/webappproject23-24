package dev.eventplaner.service;

import dev.eventplaner.model.User;
import dev.eventplaner.repository.UserRepository;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Logger instance for this class, used to log system messages, warnings, and
    // errors.
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

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
    public User getUser(UUID userID) {
        log.info("get user by userID: {}", userID);
        User user = userRepository.get(userID);
        if (user == null) {
            log.warn("User with ID {} not found", userID);
        }
        return user;
    }

    /**
     * Creates a new user.
     *
     * @param user The User object to create.
     */
    public void createUser(User user) {
        log.info("User Created: {}", user.getUserID());
        userRepository.put(user.getUserID(), user);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userID The ID of the user to delete.
     */
    public void deleteUser(UUID userID) {
        if (userRepository.get(userID) == null) {
            log.warn("User with ID {} not found", userID);
        }
        if (userRepository.get(userID) != null) {
            log.warn("User with ID {} found", userID);
            userRepository.remove(userID);
            log.info("User Deleted: {}", userID);
        }

    }

    /**
     * Updates a user.
     *
     * @param user The User object to update.
     */
    public void updateUser(User user) {
        log.info("User Updated: {}", user.getUserID());
        userRepository.put(user.getUserID(), user);
    }

    /**
     * Retrieves all users.
     *
     * @return An Iterable of all User objects.
     */
    public Iterable<User> getAll() {
        log.info("getAllUsers");
        return userRepository.values();
    }

}
