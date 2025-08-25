# ludotheca-share-mesh

# 📚 Ludotheca Share Mesh

A resource sharing system implemented as a book lending platform using microservices architecture with Spring Boot, Spring Cloud, Docker, and Google Kubernetes Engine (GKE).

---

## 🌐 Overview

This project demonstrates how microservices can be used to build scalable, maintainable cloud-native applications. It is based on a reusable resource-sharing model and was designed for educational, architectural, and deployment demonstration purposes.

Further analysis of this project can be found at: https://architectural-patterns-in-microservices.hashnode.dev/

---

## ⚙️ Key Features

- **Domain Model:** Book Lending System (generalized for other resource sharing systems like tool libraries).
- **Architecture:** Distributed microservices, API-only communication, service discovery with Eureka and Kubernetes, loose coupling and data/service isolation.
- **Technology Stack:** Java Spring Boot and Spring Cloud, Kubernetes on Google Cloud Platform, Docker containers.
- **Microservices:** `member-service`, `book-service`, `lending-service`, with Spring Cloud API Gateway.
- **DevOps & Deployment:** Dockerized services, shell scripts for automation, blue-green deployments with rollback support.
- **Testing & Quality:** TDD with JUnit and MockMVC, domain-driven design and SOLID principles.
- **Monitoring:** GCP-native monitoring and logging solutions.

---

## 📦 Repository

[github.com/marco13-moo/ludotheca-share-mesh](https://github.com/marco13-moo/ludotheca-share-mesh)

---

## 🛠️ Pre-Requisites

- A Google Cloud Platform (GCP) account.
- A GCP project with billing enabled. This README refers to it as `gcpproject`.

---

## 🚀 Deployment Instructions

### 1. Clone the Kubernetes Configuration Repository

```bash
git clone [<URL_TO_kubernetes-gke-configs>](https://github.com/marco13-moo/ludotheca-share-mesh)
cd kubernetes-gke-configs/FinalisedSCRIPTS
```
### 2. Clone the Microservices from GitLab

```bash
cp gitlabGKESRClone.sh /your/microservices-directory
cd /your/microservices-directory
chmod +x gitlabGKESRClone.sh
./gitlabGKESRClone.sh
```
### 3. Configure Application Properties
Option 1: Use MySQL (Recommended)
In each microservice's application.yml, replace all instances of localhost with mysqlservice. 
See ApplicationPropertiesFile/ in kubernetes-gke-configs for examples.

Note: Table creation is handled automatically by Spring JPA.

Option 2: Use In-Memory H2 DB (For Testing Only)
In each microservice's application.yml:
Comment out all MySQL config from spring.datasource to hibernate
Uncomment all H2-related config
All data will be lost upon termination.

### 4. Remove Eureka References
Ensure there is no mention of Eureka in any application.yml file, including the API Gateway.

### 5. Copy Deployment and Build Files
From kubernetes-gke-configs, copy the following into your microservices directory:
```bash
cp FinalisedSCRIPTS/buildDockerImageGCPKubernetesSR.sh .
cp FinalisedKubeSRYamlGKE/bookDeployAndService.yml .
cp FinalisedKubeSRYamlGKE/gatewayDeployAndService.yml .
cp FinalisedKubeSRYamlGKE/mysqlservice.yml .
cp FinalisedKubeSRYamlGKE/memberDeployAndService.yml .
cp FinalisedKubeSRYamlGKE/loanDeployAndService.yml .
```
🧳 Upload Microservices Directory to GCP
From your local machine:
```bash
scp -r /your/microservices-directory <your-gcp-cloud-shell-instance>
```
🐳 Build Docker Images on GCP
Once in your Cloud Shell:
```bash
chmod +x buildDockerImageGCPKubernetesSR.sh
./buildDockerImageGCPKubernetesSR.sh <version_number>
```
Ensure the correct image versions are reflected in the deployment YAMLs.

☁️ Create and Connect to a GKE Cluster
Use the GCP Console to create a GKE cluster with default settings.

Once ready:
```bash
chmod +x deployFinalisedKubeSRYamlGKE.sh
./deployFinalisedKubeSRYamlGKE.sh
```
🌐 Interacting with Services
You can now access your services through the external IP of your GKE cluster.

Option 1: REST Tool or Web Browser
http://<external-ip>:<port>/<service>/<serviceAction>
Ports:
member-service: 8282
loan-service: 8383
book-service: 8282

Examples:
/member/members
/loan/checkOutLoan
/book/bookById

Option 2: Using Curl
curl http://<external-ip>:8282/member/members
📈 Scaling a Service

kubectl scale deployment <deployment-name> --replicas=3
⬆️ Deploying an Upgrade
Follow Google’s guide for container upgrades:
https://codelabs.developers.google.com/codelabs/cloud-springboot-kubernetes#9

```bash
./mvnw -DskipTests package \
  com.google.cloud.tools:jib-maven-plugin:build \
  -Dimage=gcr.io/$GOOGLE_CLOUD_PROJECT/image-name:v2

kubectl set image deployment/<deployment-name> \
  <container-name>=gcr.io/$GOOGLE_CLOUD_PROJECT/image-name:v2
```
↩️ Rolling Back a Deployment
```bash
kubectl rollout undo deployment/<deployment-name>
```

🎓 Educational Value
- **Teaches modular decomposition and domain-driven design.
- **SOLID principles applied across independently deployable services.
- **Architecture showcases microservices trade-offs: complexity vs scalability.
- **Enables experimentation with real cloud-native tools: Spring Boot, Kubernetes, GCP.


