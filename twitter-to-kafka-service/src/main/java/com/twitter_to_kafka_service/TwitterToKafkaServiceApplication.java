package com.twitter_to_kafka_service;

import com.twitter_to_kafka_service.config.ConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

/*
application initialization logics
* @PostConstruct vs ApplicationListener vs CommandLineRunner vs @EventListener
* */
@SpringBootApplication
// below one is not recommended
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);
    private final ConfigData configData;

    public TwitterToKafkaServiceApplication(ConfigData configData) {
        this.configData = configData;
    }


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
       LOG.info("initiating");
       LOG.info(String.valueOf(configData.getTwitterKeywords()));
    }
}
