#!/bin/bash

set -e

# 🔧 Paramètres personnalisables
DOCKER_REGISTRY="docker.io"                # ou gcr.io, ghcr.io, etc.
DOCKER_USERNAME="elfn"             # ⚠️ À adapter
IMAGE_NAME="producer-app"
TAG="latest"

FULL_IMAGE_NAME="$DOCKER_REGISTRY/$DOCKER_USERNAME/$IMAGE_NAME:$TAG"

echo "🧼 Nettoyage et compilation du projet Maven..."
mvn clean package -DskipTests

# 📦 Récupère le .jar
JAR_FILE=$(ls target/*.jar | grep -v 'original' | head -n 1)

if [ ! -f "$JAR_FILE" ]; then
  echo "❌ Aucun fichier JAR trouvé."
  exit 1
fi

echo "📁 JAR détecté : $JAR_FILE"

echo "🔨 Construction de l'image Docker..."
docker build --build-arg JAR_NAME=$(basename "$JAR_FILE") -t "$FULL_IMAGE_NAME" .

echo "🔐 Connexion au registre Docker..."
docker login $DOCKER_REGISTRY

echo "📤 Push de l'image vers $FULL_IMAGE_NAME"
docker push "$FULL_IMAGE_NAME"

echo "✅ Image poussée avec succès sur le registre."
