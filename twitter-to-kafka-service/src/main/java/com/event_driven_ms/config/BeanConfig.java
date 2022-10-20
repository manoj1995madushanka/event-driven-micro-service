package com.event_driven_ms.config;

import com.event_driven_ms.runner.StreamRunner;
import com.event_driven_ms.runner.impl.TwitterKafkaStreamRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public RetryTemplate retryTemplate(){
        return new RetryTemplate();
    }
}
