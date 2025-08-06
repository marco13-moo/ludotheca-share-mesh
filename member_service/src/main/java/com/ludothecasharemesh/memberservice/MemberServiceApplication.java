package com.ludothecasharemesh.memberservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication //for api generation
@OpenAPIDefinition(info = @Info(title = "Member Service API", version = "1.0", description = "API Documentation for Member Microservice")) //http://localhost:8181/v3/api-docs
//@EnableDiscoveryClient
public class MemberServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApplication.class, args);
    }

    @Bean
    //@LoadBalanced //load balance rest template. Works if there is no API gateway in place
    public RestTemplate restTemplate(){
        return new RestTemplate(); //allows for consumption of RESTful web services in the application
    } // For more info: https://www.tutorialspoint.com/spring_boot/spring_boot_rest_template.htm

}

//A great tutorial by Java Techie that helped me in setting CRUD operations with JPA in Spring whilst adhering to DDD
//https://www.youtube.com/watch?v=IucFDX3RO9U&t=569s