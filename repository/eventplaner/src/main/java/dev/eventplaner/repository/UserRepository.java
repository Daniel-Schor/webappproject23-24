package dev.eventplaner.repository;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import dev.eventplaner.model.User;

@Repository
public class UserRepository extends HashMap<UUID, User> {


}
