package com.elfn.consumer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Représente un événement consommé depuis Kafka et persisté en base de données.
 *
 * Cette entité est mappée à la table "event" dans PostgreSQL.
 * Elle contient les informations minimales nécessaires à la traçabilité d’un message Kafka,
 * notamment l’identifiant de l’événement (`eventId`) et son horodatage (`timestamp`).
 *
 * Utilisée côté consommateur pour enregistrer les événements dans une table relationnelle.
 *
 * Champs :
 * - {@code id} : identifiant interne auto-généré.
 * - {@code eventId} : identifiant unique de l’événement (venant du producteur).
 * - {@code timestamp} : date/heure de l’événement.
 *
 * @author Elimane
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event")
public class EventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;
    private Instant timestamp;

}
