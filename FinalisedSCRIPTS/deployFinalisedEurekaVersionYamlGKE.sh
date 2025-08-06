#!/bin/bash
kubectl apply -f mysqlservice.yml
kubectl apply -f EurekaDeployAndService.yml
kubectl apply -f 4ServicesDeployAndService.yml