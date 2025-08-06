#!/bin/bash
git clone https://gitlab.cs.st-andrews.ac.uk/ms4t/gateway_service.git
git clone https://gitlab.cs.st-andrews.ac.uk/ms4t/lending_service.git
git clone https://gitlab.cs.st-andrews.ac.uk/ms4t/book_service.git
git clone https://gitlab.cs.st-andrews.ac.uk/ms4t/member_service.git
cd book_service
git checkout KubeDiscoveryBranchBook
cd ..
cd member_service
git checkout KubeDiscoveryBranchMember
cd ..
cd lending_service
git checkout KubeDiscoveryBranchLending
cd ..
cd gateway_service
git checkout KubeDiscoveryBranchGateway
cd ..