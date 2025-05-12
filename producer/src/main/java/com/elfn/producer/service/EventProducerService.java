package com.elfn.producer.service;

import com.elfn.producer.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Service responsable de la production et de l'envoi d'événements vers Kafka.
 *
 * Cette classe utilise un KafkaTemplate pour publier des messages dans le topic "events-topic".
 * Chaque message contient un identifiant unique et un timestamp, générés automatiquement.
 *
 * @author Elimane
 */
@Service
public class EventProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EventProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Envoie un événement JSON (UUID + timestamp) dans le topic Kafka "events-topic".
     * L'identifiant est utilisé comme clé Kafka pour éviter les doublons dans la même partition.
     *
     * @param event événement à envoyer
     */
    public void sendEvent(Event event) {
        try {
            // Génération automatique de l'ID et du timestamp si non fournis
            if (event.getEventId() == null || event.getEventId().isEmpty()) {
                event.setEventId(UUID.randomUUID().toString());
            }
            if (event.getTimestamp() == null) {
                event.setTimestamp(Instant.now());
            }

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String message = objectMapper.writeValueAsString(event);

            // Envoi du message avec l'ID comme clé
            kafkaTemplate.send("events-topic", event.getEventId(), message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
