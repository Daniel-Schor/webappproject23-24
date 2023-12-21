package dev.eventplaner.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import dev.eventplaner.model.Event;

@Repository
public class EventRepository extends HashMap<Long, Event> {


}
