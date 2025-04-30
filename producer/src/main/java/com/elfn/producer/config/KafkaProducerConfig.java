package com.elfn.producer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Elimane
 */
/**
 * Configuration Kafka pour l'application Producteur.
 *
 * Cette classe définit les beans nécessaires à l'envoi de messages vers Kafka,
 * notamment le ProducerFactory et le KafkaTemplate utilisés dans les services.
 */
public class KafkaProducerConfig {


    /**
     * Crée une ProducerFactory configurée pour Kafka.
     *
     * Elle utilise le broker "kafka:9092" et des sérialiseurs String pour les clés et valeurs.
     *
     * @return une instance de ProducerFactory
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Crée un KafkaTemplate basé sur la ProducerFactory.
     *
     * Le KafkaTemplate est utilisé pour envoyer des messages à un topic Kafka.
     *
     * @return une instance de KafkaTemplate
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
