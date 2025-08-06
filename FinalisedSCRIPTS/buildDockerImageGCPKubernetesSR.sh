#!/bin/bash

gcloud services enable containerregistry.googleapis.com

cd book_service
./mvnw -DskipTests package
export GOOGLE_CLOUD_PROJECT=`gcloud config list --format="value(core.project)"`
./mvnw -DskipTests com.google.cloud.tools:jib-maven-plugin:build \
  -Dimage=gcr.io/$GOOGLE_CLOUD_PROJECT/book-service-kubesrnew:v$1
cd ..
cd member_service
./mvnw -DskipTests package
export GOOGLE_CLOUD_PROJECT=`gcloud config list --format="value(core.project)"`
./mvnw -DskipTests com.google.cloud.tools:jib-maven-plugin:build \
  -Dimage=gcr.io/$GOOGLE_CLOUD_PROJECT/member-service-kubesrnew:v$1
cd ..
cd lending_service
./mvnw -DskipTests package
export GOOGLE_CLOUD_PROJECT=`gcloud config list --format="value(core.project)"`
./mvnw -DskipTests com.google.cloud.tools:jib-maven-plugin:build \
  -Dimage=gcr.io/$GOOGLE_CLOUD_PROJECT/lending-service-kubesrnew:v$1
cd ..
cd gateway_service
./mvnw -DskipTests package
export GOOGLE_CLOUD_PROJECT=`gcloud config list --format="value(core.project)"`
./mvnw -DskipTests com.google.cloud.tools:jib-maven-plugin:build \
  -Dimage=gcr.io/$GOOGLE_CLOUD_PROJECT/gateway-service-kubesrnew:v$1
cd ..