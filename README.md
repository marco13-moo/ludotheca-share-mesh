# ludotheca-share-mesh

Developed a resource sharing platform—implemented as microservices running in Docker containers on Google Kubernetes Engine (GKE). Key achievements include:

System Design: Selected the book lending domain as a representative example for a family of resource sharing systems (e.g., libraries, tools).
Technology Stack: Utilized Java Spring Boot and Spring Cloud, deployed on Kubernetes hosted via Google Cloud Platform.
Architecture: Designed a distributed microservices architecture emphasizing loose coupling, service discovery, API-only communication, and data/service isolation.
Microservices: Decomposed into lending, book, and member microservices, independently deployable and scalable via Spring Cloud API Gateway on Kubernetes.
Quality Assurance: Used test-driven development with JUnit and MockMVC for comprehensive testing and clean code.
Deployment: Automated deployment with shell scripts; implemented blue-green deployment for seamless updates and rollbacks.
Monitoring & Resilience: Integrated GCP monitoring; implemented Eureka and Kubernetes service discovery and asynchronous messaging.
Educational Value: Demonstrated SOLID principles and modular, scalable cloud-native design.
Critical Reflection: Addressed microservices complexity and trade-offs versus monolithic architectures.
Repository: github.com/marco13-moo/ludotheca-share-mesh
