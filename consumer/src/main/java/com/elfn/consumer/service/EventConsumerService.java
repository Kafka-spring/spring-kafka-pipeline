package com.elfn.consumer.service;

import com.elfn.consumer.dto.EventDTO;
import com.elfn.consumer.model.EventLog;
import com.elfn.consumer.repository.EventLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: Elimane
 *
 * Service de consommation Kafka pour les événements.
 */
@Service
@Slf4j
public class EventConsumerService {
    private final EventLogRepository repository;
    private final ObjectMapper objectMapper;

    public EventConsumerService(EventLogRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    /**
     * Consomme un message Kafka, extrait l'ID et le timestamp,
     * puis enregistre l'événement dans la base de données.
     *
     * @param message message reçu au format "id,timestamp"
     */
    @KafkaListener(topics = "events-topic", groupId = "my-group")
    public void consume(String message) {
//        String[] parts = message.split(",");
//        EventLog log = new EventLog();
//        log.setEventId(parts[0]);
//        log.setTimestamp(Instant.parse(parts[1]));
//        repository.save(log);
        try {
            EventDTO dto = objectMapper.readValue(message, EventDTO.class);
            EventLog logMessage = new EventLog();
            logMessage.setEventId(dto.getId());
            logMessage.setTimestamp(dto.getTimestamp());

            repository.save(logMessage);
            log.info("✅ Événement persisté avec succès : {}", dto.getId());
        } catch (Exception e) {
            log.error("❌ Échec de la désérialisation du message : {}", message, e);
        }
    }
}

