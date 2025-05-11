package com.elfn.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @Author: Elimane
 *
 * Représentation de transfert de données (DTO) pour un événement consommé depuis Kafka.
 *
 * Cette classe est utilisée pour désérialiser les messages JSON reçus depuis le topic Kafka,
 * avant leur transformation en entités persistées en base de données.
 *
 * Elle contient :
 * - un identifiant unique de l’événement (id),
 * - un horodatage de création (timestamp).
 *
 * Elle facilite la séparation des responsabilités entre la couche de transport (Kafka)
 * et la couche de persistance (entité JPA).
 *
 * @author Elimane
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private String id;
    private Instant timestamp;

}
