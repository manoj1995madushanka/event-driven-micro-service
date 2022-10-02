package com.twitter_to_kafka_service.runner.impl;

import com.twitter_to_kafka_service.config.ConfigData;
import com.twitter_to_kafka_service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
//@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-v2-tweets",havingValue = "true",matchIfMissing = true)
@ConditionalOnExpression("${twitter-to-kafka-service.enable-v2-tweets} && not ${twitter-to-kafka-service.enable-mock-tweets}")
public class TwitterV2KafkaStreamRunner implements StreamRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterV2KafkaStreamRunner.class);

    private final ConfigData configData;
    private final TwitterV2StreamHelper twitterV2StreamHelper;

    public TwitterV2KafkaStreamRunner(ConfigData configData, TwitterV2StreamHelper twitterV2StreamHelper) {
        this.configData = configData;
        this.twitterV2StreamHelper = twitterV2StreamHelper;
    }

    @Override
    public void start() {
        String bearerToken = configData.getTwitterV2BearerToken();
        if (bearerToken != null) {
            try {
                twitterV2StreamHelper.setupRules(bearerToken, getRules());
                twitterV2StreamHelper.connectStream(bearerToken);
            } catch (IOException | URISyntaxException e) {
                LOG.error("Error streaming tweets: ",e);
                throw new RuntimeException("Error streaming tweets: ",e);
            }

        } else {
            LOG.error("There was a problem getting your bearer token." + "Please make sure you set the TWITTER_BEARER_TOKEN environment variable");
            throw new RuntimeException("There was a problem getting your bearer token." + "Please make sure you set the TWITTER_BEARER_TOKEN environment variable");
        }
    }

    private Map<String, String> getRules() {
        List<String> keywords = configData.getTwitterKeywords();
        Map<String, String> rules = new HashMap<>();
        for (String keyword : keywords) {
            rules.put(keyword, "Keyword: " + keyword);
        }
        LOG.info("Created filter for twitter Stream for keywords: {}", keywords);
        return rules;
    }
}
