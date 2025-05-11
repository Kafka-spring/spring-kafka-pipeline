package com.elfn.consumer.service;

import com.elfn.consumer.model.EventLog;
import com.elfn.consumer.repository.EventLogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @Author: Elimane
 *
 * Service de consommation Kafka pour les événements.
 */
@Service
public class EventConsumerService {
    private final EventLogRepository repository;

    public EventConsumerService(EventLogRepository repository) {
        this.repository = repository;
    }

    /**
     * Consomme un message Kafka, extrait l'ID et le timestamp,
     * puis enregistre l'événement dans la base de données.
     *
     * @param message message reçu au format "id,timestamp"
     */
    @KafkaListener(topics = "events-topic", groupId = "my-group")
    public void consume(String message) {
        String[] parts = message.split(",");
        EventLog log = new EventLog();
        log.setEventId(parts[0]);
        log.setTimestamp(Instant.parse(parts[1]));
        repository.save(log);
    }
}

