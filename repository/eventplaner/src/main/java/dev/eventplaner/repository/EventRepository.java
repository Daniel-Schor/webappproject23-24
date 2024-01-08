package dev.eventplaner.repository;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import dev.eventplaner.model.Event;

@Repository
public class EventRepository extends HashMap<UUID, Event> {


}
