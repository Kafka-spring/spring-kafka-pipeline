#!/bin/bash

# 📁 Dossier de sortie
mkdir -p certs
cd certs

# 🔑 Variables
PASSWORD=pass
DNAME="CN=localhost, OU=Kafka, O=Company, L=Paris, ST=IDF, C=FR"

# 📦 Keystore Kafka Broker
keytool -genkey -alias kafka-server \
  -keyalg RSA -keystore kafka.server.keystore.jks \
  -keypass $PASSWORD -storepass $PASSWORD \
  -validity 365 -dname "$DNAME"

# 🧾 Truststore Kafka Broker (auto-signé)
keytool -export -alias kafka-server -file kafka-server-cert.cer \
  -keystore kafka.server.keystore.jks -storepass $PASSWORD

keytool -import -alias kafka-server -file kafka-server-cert.cer \
  -keystore kafka.server.truststore.jks -storepass $PASSWORD -noprompt

# 📁 Création des fichiers de mot de passe
echo $PASSWORD > keystore_creds
echo $PASSWORD > truststore_creds
echo $PASSWORD > key_creds

echo "✅ Certificats et fichiers de mot de passe générés dans ./certs"

