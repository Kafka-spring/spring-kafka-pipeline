package com.elfn.producer.service;

import com.elfn.producer.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Service responsable de la production et de l'envoi d'événements vers Kafka.
 *
 * Cette classe utilise un KafkaTemplate pour publier des messages dans le topic "events-topic".
 * Chaque message contient un identifiant unique et un timestamp, générés automatiquement.
 *
 * L'envoi est planifié à une fréquence régulière (toutes les 100 millisecondes) grâce à l'annotation @Scheduled.
 *
 * @author Elimane
 */
@Service
public class EventProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Injection du KafkaTemplate utilisé pour publier les messages dans Kafka.
     *
     * @param kafkaTemplate le producteur Kafka configuré
     */
    public EventProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Envoie toutes les 100 ms un événement JSON (UUID + timestamp) dans le topic Kafka "events-topic".
     */
    @Scheduled(fixedRate = 100)
    public void sendEvent() {
        try {
            Event event = new Event();
            event.setId(UUID.randomUUID().toString());
            event.setTimestamp(Instant.now());

            ObjectMapper objectMapper = new ObjectMapper();
            String message = objectMapper.writeValueAsString(event);

            kafkaTemplate.send("events-topic", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
