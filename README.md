# Store catalog microservice

## Prerequisites

```bash
docker run -d --name pg-store-catalog -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=store-catalog -p 5432:5432 postgres:13
```

## Build and run commands
```bash
mvn clean package
cd api/target
java -jar store-catalog-api-1.0.0-SNAPSHOT.jar
```
Available at: localhost:8080/v1/stores

## Run in IntelliJ IDEA
Add new Run configuration and select the Application type. In the next step, select the module api and for the main class com.kumuluz.ee.EeApplication.

Available at: localhost:8080/v1/stores

## Docker commands
```bash
docker build -t store-catalog .   
docker images
docker run store-catalog    
docker tag store-catalog anzeo/store-catalog 
docker push anzeo/store-catalog
docker ps
```

```bash
docker network create primerjalnik
docker run -d --name pg-store-catalog -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=store-catalog -p 5432:5432 --network primerjalnik postgres:13
docker inspect pg-store-catalog
docker run -p 8080:8080 --network primerjalnik -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-store-catalog:5432/store-catalog anzeo/store-catalog
```

## Kubernetes
```bash
kubectl version
kubectl --help
kubectl get nodes
kubectl create -f deployment.yaml 
kubectl apply -f deployment.yaml
kubectl get services 
kubectl get deployments
kubectl get pods
kubectl logs store-catalog-deployment-6f59c5d96c-rjz46
kubectl delete pod store-catalog-deployment-6f59c5d96c-rjz46
```