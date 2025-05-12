package com.elfn.producer.service;

import com.elfn.producer.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitaire pour le service EventProducerService.
 * @author Elimane
 */
class EventProducerServiceTest {

    @Test
    void testSendEvent_publieMessageKafka() throws Exception {
        // Mock du KafkaTemplate
        KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);

        // Service à tester
        EventProducerService service = new EventProducerService(kafkaTemplate);

        Event event = new Event();
        event.setEventId(UUID.randomUUID().toString());
        event.setTimestamp(Instant.now());

        // Appel de la méthode
        service.sendEvent(event);

        // Capture de l'argument envoyé au KafkaTemplate
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq("events-topic"), captor.capture());

        // Lecture du message capturé
        String jsonMessage = captor.getValue();

        // Configuration ObjectMapper pour Instant
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Event eventDeserialized = mapper.readValue(jsonMessage, Event.class);

        // Vérifications
        assertNotNull(eventDeserialized.getEventId());
        assertDoesNotThrow(() -> UUID.fromString(eventDeserialized.getEventId()));
        assertNotNull(eventDeserialized.getTimestamp());
        assertTrue(eventDeserialized.getTimestamp().isBefore(Instant.now().plusSeconds(1)));
    }

}
