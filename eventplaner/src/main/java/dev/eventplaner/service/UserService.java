package dev.eventplaner.service;

import dev.eventplaner.model.User;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.eventplaner.repository.UserRepository;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User getUser(UUID userID)  {
        log.info("get user by userID: {}", userID);
        User user = userRepository.get(userID);
        if (user == null) {
            log.warn("User with ID {} not found", userID);
        }
        return user;
    }
    
    public void createUser(User user) {
        log.info("User Created: {}", user.getUserID());
        userRepository.put(user.getUserID(), user);
    }

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

    public void updateUser(User user) {
        log.info("User Updated: {}", user.getUserID());
        userRepository.put(user.getUserID(), user);
    }

    public Iterable<User> getAll() {
        log.info("getAllUsers");
        return userRepository.values();
    }



}
