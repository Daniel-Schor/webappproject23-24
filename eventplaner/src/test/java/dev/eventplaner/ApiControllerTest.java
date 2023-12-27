package dev.eventplaner;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import dev.eventplaner.controller.ApiController;
import dev.eventplaner.model.Coordinates;
import dev.eventplaner.model.Event;
import dev.eventplaner.service.EventService;

@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private ApiController apiController;

    @Test
    public void testGetAllEvents() throws Exception {
        // Arrange
        Event event1 = new Event("Event 1", "Description 1", LocalDateTime.now(), new Coordinates(1.1,2.2), 0, UUID.randomUUID());
        Event event2 = new Event("Event 2", "Description 2", LocalDateTime.now(), new Coordinates(1.1,2.2), 0, UUID.randomUUID());
        Collection<Event> events = Arrays.asList(event1, event2);

        when(eventService.getAll()).thenReturn(events);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/events")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"name\":\"Event 1\"},{\"name\":\"Event 2\"}]"));
    }
}