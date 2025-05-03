# 🔄 kafka-event-pipeline

## 🧰 Producteur/Consommateur Kafka avec Spring Boot + Monitoring Kafdrop

### ❗ Problème à résoudre :  
### **"📬 Publier des événements via une API REST → Kafka → Consommateur → Stockage en base PostgreSQL"**

---
### 🔁🌐 Fonctionnement
Ce projet simule une chaîne d’événements complète :

1. Postman envoie un `POST /api/events`
2. Le Producteur Spring Boot publie l’événement sur un topic Kafka
3. Le Cluster Kafka réplique le message sur 3 brokers
4. Le Consommateur Spring Boot lit et sauvegarde les événements dans PostgreSQL
5. Kafdrop permet d’inspecter les messages en temps réel

---

### 🧾 Fonctionnalités principales

- ⚖️ Répartition automatique des partitions sur 3 brokers Kafka
- 📮 API REST pour publier les événements
- 👂 Consumer qui écoute le topic `events-topic`
- 💾 Persistance des messages dans PostgreSQL
- 🧭 Kafdrop UI pour visualiser les topics et partitions Kafka
- 🐳 Infrastructure complète via `docker-compose`

---

### 🛠️ Stack technique

| Composant | Description |
|-----------|-------------|
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="24"/> Spring Boot | Producteur & Consommateur Kafka |
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" width="24"/> PostgreSQL | Base de données pour le stockage des événements |
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" width="24"/> Docker Compose | Déploiement multi-conteneurs |
| <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/apachekafka/apachekafka-original-wordmark.svg" width="24"/> Apache Kafka | Broker de messages Kafka |
| <img src="https://raw.githubusercontent.com/obsidiandynamics/kafdrop/master/src/main/resources/static/images/kafdrop-logo.png" width="24"/> Kafdrop | Interface web pour surveiller Kafka |


---

## 🛢️ Structure de la base de données

| id | uuid | timestamp |

---

## 📊 Diagramme de flux



```mermaid
flowchart TD
  subgraph DockerCompose

    subgraph Producteur
      A[Postman] -->|POST /api/events| B[Spring Boot - Producer]
    end

    subgraph Cluster Kafka
      B -->|send message| C[Kafka Topic: events-topic]
    end

    subgraph KafkaCluster
      C --> P1[Partition 1 - Broker 1]
      C --> P2[Partition 2 - Broker 2]
      C --> P3[Partition 3 - Broker 3]
    end

    subgraph Monitoring
      K[Kafdrop UI] --> C
    end

    subgraph Consommateur
      D[Spring Boot - Consumer] -->|consume| C
      D -->|persist| DB[(PostgreSQL DB)]
    end

  end




