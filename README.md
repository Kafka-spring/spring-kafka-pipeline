# spring-kafka-k8s-uc
📡 kafka-event-pipeline
🧰 Producteur/Consommateur Kafka avec Spring Boot + Monitoring Kafdrop
❗ Problème à résoudre :
"📬 Publier des événements via une API REST → Kafka → Consommateur → Stockage en base PostgreSQL"
🔍
Ce projet simule une chaîne d’événements complète :

Postman envoie un POST /api/events

Producteur Spring Boot publie l’événement sur le topic Kafka

Cluster Kafka réplique l’événement sur 3 brokers

Consommateur Spring Boot lit et sauvegarde les événements dans PostgreSQL

Kafdrop permet d’inspecter les messages en temps réel

🧾 Fonctionnalités principales
🔁 Répartition automatique des partitions sur 3 brokers Kafka

📤 API REST pour publier des événements

📥 Consumer qui écoute le topic events-topic

🛢️ Persistance des messages dans PostgreSQL

🔍 Kafdrop UI pour visualiser les topics et partitions Kafka

🐳 Infrastructure complète via docker-compose

🛠️ Stack technique
Composant	Description
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="24"/> Spring Boot	Producteur & consommateur Kafka
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/kafka/kafka-original.svg" width="24"/> Apache Kafka	Broker de messages
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" width="24"/> Docker Compose	Déploiement multi-conteneurs
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" width="24"/> PostgreSQL	Stockage des événements
<img src="https://img.icons8.com/fluency/48/topic.png" width="24"/> Kafdrop	Interface web pour surveiller Kafka
📂 Structure du projet
kafka-event-pipeline/
├── producer/              → Spring Boot Producer REST
├── consumer/              → Spring Boot Consumer
├── docker-compose.yml     → Déploiement Zookeeper, Kafka, Kafdrop, PostgreSQL, Producer, Consumer
├── kafdrop/               → UI de visualisation Kafka
└── README.md
🛢️ Structure de la base de données
Table : events
--------------------------
| id  | uuid  | timestamp |
--------------------------
📊 Diagramme de flux
flowchart TD
    subgraph DockerCompose

        subgraph Client
            A[Postman] -->|POST /api/events| B[API REST - Producer]
        end

        subgraph Producteur
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
