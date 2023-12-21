package dev.eventplaner.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import dev.eventplaner.model.User;

@Repository
public class UserRepository extends HashMap<Long, User> {


}
