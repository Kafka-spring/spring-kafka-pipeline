#!/bin/bash

echo "🛑 Arrêt et nettoyage complet..."
docker-compose down -v --remove-orphans

echo "🚀 Redémarrage complet avec reconstruction..."
docker-compose up --build --force-recreate

