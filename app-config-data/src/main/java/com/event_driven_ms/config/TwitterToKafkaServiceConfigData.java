package com.event_driven_ms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")// these properties will get from
// twitter-to-kafka-service properties file
@Data //  this lombok annotation updates class with additional methods during compilation
public class TwitterToKafkaServiceConfigData {
    private List<String> twitterKeywords;
    private String welcomeMessage;
    private String twitterV2BaseUrl;
    private String twitterV2RulesBaseUrl;
    private String twitterV2BearerToken;

    // mock twitter stream properties
    private Boolean enableMockTweets;
    private Long mockSleepMs;
    private Integer mockMinTweetLength;
    private Integer mockMaxTweetLength;
}
