package com.microservices.demo.reactive.elastic.query.web.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
public class ReactiveElasticQueryWebClient {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveElasticQueryWebClient.class, args);

    }
}