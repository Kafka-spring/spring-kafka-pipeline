package com.elfn.consumer.config;

import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration Kafka pour la consommation de messages.
 *
 * Cette classe configure :
 * - Le {@link ConsumerFactory} nécessaire à la création de consommateurs Kafka,
 * - Un {@link ConcurrentKafkaListenerContainerFactory} pour permettre l'utilisation de l'annotation {@code @KafkaListener}.
 *
 * Les messages sont consommés en mode texte (clé et valeur en {@code String}).
 *
 * ⚙️ Paramètres configurés :
 * - Adresse du broker Kafka : {@code kafka:9092}
 * - ID de groupe de consommateurs : {@code my-group}
 * - Désérialiseurs pour les clés et les valeurs : {@link StringDeserializer}
 *
 * @author Elimane
 */
public class KafkaConsumerConfig {

    /**
     * Définit la configuration de base d’un consommateur Kafka.
     * @return une factory de consommateurs Kafka
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    /**
     * Fournit le container factory utilisé pour connecter les listeners Kafka (annotation {@code @KafkaListener}).
     * @return une factory de conteneur Kafka concurrent
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
