package com.elfn.consumer.service;

import com.elfn.consumer.dto.EventDTO;
import com.elfn.consumer.model.EventLog;
import com.elfn.consumer.repository.EventLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: Elimane
 *
 * Classe de test unitaire pour {@link EventConsumerService}.
 * Elle vérifie que la désérialisation d’un message JSON fonctionne correctement
 * et que l’événement est bien persisté via le repository.
 */
class EventConsumerServiceTest {

    private EventLogRepository repository;
    private ObjectMapper objectMapper;
    private EventConsumerService service;


    /**
     * Prépare les objets mockés et l'instance du service à tester avant chaque test.
     */
    @BeforeEach
    void setUp() {
        repository = mock(EventLogRepository.class);
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        service = new EventConsumerService(repository, objectMapper);
    }

    /**
     * Vérifie qu’un message JSON valide est correctement désérialisé
     * et enregistré dans la base de données.
     */
    @Test
    void shouldDeserializeAndPersistEventSuccessfully() throws Exception {
        // GIVEN
        EventDTO dto = new EventDTO("abc123", Instant.parse("2025-05-02T21:45:00Z"));
        String jsonMessage = objectMapper.writeValueAsString(dto);

        // WHEN
        service.consume(jsonMessage);

        // THEN
        ArgumentCaptor<EventLog> captor = ArgumentCaptor.forClass(EventLog.class);
        verify(repository, times(1)).save(captor.capture());

        EventLog saved = captor.getValue();
        assertThat(saved.getEventId()).isEqualTo("abc123");
        assertThat(saved.getTimestamp()).isEqualTo("2025-05-02T21:45:00Z");
    }

    /**
     * Vérifie que si le message JSON est invalide,
     * aucun enregistrement n’est effectué dans la base.
     */
    @Test
    void shouldLogErrorWhenDeserializationFails() {
        // GIVEN
        String invalidMessage = "not_a_json_string";

        // WHEN
        service.consume(invalidMessage);

        // THEN
        verify(repository, never()).save(any());
    }
}
