package com.twitter_to_kafka_service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

/*
application initialization logics
* @PostConstruct vs ApplicationListener vs CommandLineRunner vs @EventListener
* */
@SpringBootApplication
// below one is not recommended
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    /*
    * executes after dependency injection to perform initialization
    * */
    /*@PostConstruct
    public void init(){

    }*/

    @Override
    public void run(String... args) throws Exception {
        System.out.println("initiating");
    }
}
