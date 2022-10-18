package com.microservice;

import com.microservice.init.StreamInitializer;
import com.microservice.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
application initialization logics
* @PostConstruct vs ApplicationListener vs CommandLineRunner vs @EventListener
* */
@SpringBootApplication
// below one is not recommended
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);
    //private final TwitterToKafkaServiceConfigData configData;
    private final StreamRunner streamRunner;
    private final StreamInitializer streamInitializer;

    public TwitterToKafkaServiceApplication(StreamRunner streamRunner, StreamInitializer streamInitializer) {
        this.streamRunner = streamRunner;
        this.streamInitializer = streamInitializer;
    }

    /*public TwitterToKafkaServiceApplication(TwitterToKafkaServiceConfigData configData,StreamRunner runner) {
        this.configData = configData;
        this.streamRunner = runner;
    }*/


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
        //LOG.info(String.valueOf(configData.getTwitterKeywords()));
        //LOG.info(configData.getWelcomeMessage());

        // initialize kafka topics and check stream registry
        streamInitializer.init();
        // start streaming
        streamRunner.start();

    }
}
