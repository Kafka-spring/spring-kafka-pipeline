package com.elfn.producer.controller;

import com.elfn.producer.model.Event;
import com.elfn.producer.service.EventProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @Author: Elimane
 */
@WebMvcTest(EventController.class)
class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventProducerService eventProducerService;

    @Test
    void shouldSendEventSuccessfully() throws Exception {
        // Given
        Event event = new Event();
        event.setEventId("123");
        event.setTimestamp(Instant.parse("2025-05-04T12:00:00Z"));

        // When
        String json = objectMapper.writeValueAsString(event);

        // Then
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Événement envoyé : " + json));

        Mockito.verify(eventProducerService).sendEvent(event);
    }
}
