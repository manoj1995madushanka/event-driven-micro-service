package com.microservices.demo.elastic.query.web.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
public class ElasticQueryWebClientApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}