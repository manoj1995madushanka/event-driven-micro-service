package com.event_driven_ms.kafka_admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient webClient(){
        return WebClient.builder().build();
    }
}
