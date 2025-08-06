#!/bin/bash
git clone https://gitlab.cs.st-andrews.ac.uk/ms4t/gateway_service.git
git clone https://gitlab.cs.st-andrews.ac.uk/ms4t/service_registry.git
git clone https://gitlab.cs.st-andrews.ac.uk/ms4t/lending_service.git
git clone https://gitlab.cs.st-andrews.ac.uk/ms4t/book_service.git
git clone https://gitlab.cs.st-andrews.ac.uk/ms4t/member_service.git
git clone https://gitlab.cs.st-andrews.ac.uk/ms4t/loan_consumer.git
cd lending_service
git checkout RabbitMQPubSubBranchLending
cd ..