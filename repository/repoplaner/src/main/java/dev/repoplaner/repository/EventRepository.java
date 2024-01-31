package dev.repoplaner.repository;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import dev.repoplaner.model.Event;

/**
 * This class represents a repository for storing and managing events.
 * It extends the HashMap class and uses UUID as the key and Event as the value.
 */
@Repository
public class EventRepository extends HashMap<UUID, Event> {


}
