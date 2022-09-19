package com.twitter_to_kafka_service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")
@Data //  this lombok annotation updates class with additional methods during compilation
public class ConfigData {
    private List<String> twitterKeywords;
}
