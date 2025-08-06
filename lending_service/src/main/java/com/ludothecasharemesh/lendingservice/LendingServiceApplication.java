package com.ludothecasharemesh.lendingservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Lending Service API", version = "1.0", description = "API Documentation for Lending Microservice")) //http://localhost:8383/v3/api-docs.yml
//@EnableDiscoveryClient //More info: https://stackoverflow.com/questions/31976236/whats-the-difference-between-enableeurekaclient-and-enablediscoveryclient
public class LendingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LendingServiceApplication.class, args);
    }


    @Bean
    //@LoadBalanced //https://spring.io/guides/gs/spring-cloud-loadbalancer/ // With this annotation, RestTemplate has client load balancing capabilities
    public RestTemplate restTemplate(){
        return new RestTemplate(); //for more info: https://spring.io/guides/gs/consuming-rest/
    }

}

//A great tutorial by Java Techie that helped me in setting CRUD operations with JPA in Spring whilst adhering to DDD
//https://www.youtube.com/watch?v=IucFDX3RO9U&t=569s
