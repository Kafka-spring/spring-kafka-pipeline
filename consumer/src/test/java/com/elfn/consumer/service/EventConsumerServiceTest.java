package com.elfn.consumer.service;

import com.elfn.consumer.model.EventLog;
import com.elfn.consumer.repository.EventLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @Author: Elimane
 *
 * Test unitaire de la classe {@link EventConsumerService}.
 *
 * Ce test vérifie que les messages consommés depuis Kafka sont bien parsés
 * et persistés dans la base de données via {@link EventLogRepository}.
 */

class EventConsumerServiceTest {

    private EventLogRepository repository;
    private EventConsumerService consumerService;

    @BeforeEach
    void setUp() {
        repository = mock(EventLogRepository.class);
        consumerService = new EventConsumerService(repository);
    }


    /**
     * Vérifie que la méthode consume() convertit correctement un message Kafka
     * en entité {@link EventLog} et l'enregistre via le repository.
     */
    @Test
    void consume_doitEnregistrerUnEvenementDansLaBase() {
        // Given
        String message = "123e4567-e89b-12d3-a456-426614174000,2024-05-01T12:34:56.789Z";

        // When
        consumerService.consume(message);

        // Then
        ArgumentCaptor<EventLog> captor = ArgumentCaptor.forClass(EventLog.class);
        verify(repository, times(1)).save(captor.capture());

        EventLog sauvegardé = captor.getValue();
        assertThat(sauvegardé.getEventId()).isEqualTo("123e4567-e89b-12d3-a456-426614174000");
        assertThat(sauvegardé.getTimestamp()).isEqualTo(Instant.parse("2024-05-01T12:34:56.789Z"));
    }
}
