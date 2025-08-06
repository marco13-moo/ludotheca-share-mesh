#!/bin/bash
kubectl apply -f mysqlservice.yml
kubectl apply -f gatewayDeployAndService.yml
kubectl apply -f bookDeployAndService.yml
kubectl apply -f memberDeployAndService.yml
kubectl apply -f loanDeployAndService.yml