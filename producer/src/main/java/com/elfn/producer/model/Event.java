package com.elfn.producer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

/**
 * @Author: Elimane
 * <p>
 * Représente un événement métier à envoyer vers Kafka.
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String id;
    private Instant timestamp;
}
