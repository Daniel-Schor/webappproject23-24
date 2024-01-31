package dev.repoplaner.repository;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import dev.repoplaner.model.User;

/**
 * This class represents a repository for storing User objects.
 * It extends the HashMap class and uses UUID as the key and User as the value.
 * This repository provides methods for adding, retrieving, and removing User objects.
 */
@Repository
public class UserRepository extends HashMap<UUID, User> {


}
