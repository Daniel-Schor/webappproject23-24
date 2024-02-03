package dev.repoplaner.repository;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import dev.repoplaner.model.User;

@Repository
public class UserRepository extends HashMap<UUID, User> {

}
