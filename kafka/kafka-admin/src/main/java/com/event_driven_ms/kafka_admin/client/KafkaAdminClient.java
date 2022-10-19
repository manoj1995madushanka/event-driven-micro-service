package com.event_driven_ms.kafka_admin.client;

import com.event_driven_ms.config.KafkaConfigData;
import com.event_driven_ms.config.RetryConfigData;
import com.event_driven_ms.kafka_admin.exception.KafkaClientException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Component
public class KafkaAdminClient {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaAdminClient.class);

    private final KafkaConfigData kafkaConfigData;
    private final RetryConfigData retryConfigData;
    private final AdminClient adminClient;
    private final RetryTemplate retryTemplate;
    private final WebClient webClient;

    public KafkaAdminClient(KafkaConfigData kafkaConfigData, RetryConfigData retryConfigData,
                            AdminClient adminClient, RetryTemplate retryTemplate, WebClient webClient) {
        this.kafkaConfigData = kafkaConfigData;
        this.retryConfigData = retryConfigData;
        this.adminClient = adminClient;
        this.retryTemplate = retryTemplate;
        this.webClient = webClient;
    }

    public void createTopics() {
        CreateTopicsResult createTopicsResult;
        try {
            createTopicsResult = retryTemplate.execute(this::doCreateTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry for creating kafka topics", t);
        }
        checkTopicsCreated();
    }

    public void checkSchemaRegistry(){
        int retryCount=1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        Integer multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMS = retryConfigData.getSleepTimeMs();
        while (!getSchemaRegistryStatus().is2xxSuccessful()){
            checkMaxRetry(retryCount++, maxRetry);
            sleep(sleepTimeMS);
            sleepTimeMS *= multiplier;
        }
    }

    private HttpStatus getSchemaRegistryStatus(){
        try {
            return webClient.method(HttpMethod.GET)
                    .uri(kafkaConfigData.getSchemaRegistryUrl())
                    .exchange()
                    .map(ClientResponse::statusCode)
                    .block(); // block used to get synchronize result
        } catch (Exception e) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        }
    }

    public void checkTopicsCreated() {
        Collection<TopicListing> topics = getTopics();
        int retryCount = 1;
        Integer maxRetry = retryConfigData.getMaxAttempts();
        Integer multiplier = retryConfigData.getMultiplier().intValue();
        Long sleepTimeMS = retryConfigData.getSleepTimeMs();
        for (String topic : kafkaConfigData.getTopicNamesToCreate()) {
            while (!isTopicCreated(topics, topic)) {
                checkMaxRetry(retryCount++, maxRetry);
                sleep(sleepTimeMS);
                sleepTimeMS *= multiplier;
                topics = getTopics();
            }
        }
    }

    private boolean isTopicCreated(Collection<TopicListing> topics, String topicName) {
        return (topics == null) ? null : topics.stream().anyMatch(topic -> topic.name().equals(topicName));
    }

    private void checkMaxRetry(int retryIndex, Integer maxRetry) {
        if (retryIndex > maxRetry) {
            throw new KafkaClientException("Reaching max number of retry for reading kafka topics");
        }
    }

    private void sleep(Long sleepTimeMS) {
        try {
            Thread.sleep(sleepTimeMS);
        } catch (InterruptedException e) {
            throw new KafkaClientException("Error while sleeping for waiting new created topics", e);
        }
    }

    private CreateTopicsResult doCreateTopics(RetryContext retryContext) {
        List<String> topicNames = kafkaConfigData.getTopicNamesToCreate();
        LOG.info("Creating {} topics, attempt {}", topicNames.size(), retryContext.getRetryCount());
        List<NewTopic> kafkaTopics = topicNames.stream().map(topic -> {
            return new NewTopic(topic.trim(), kafkaConfigData.getNumOfPartitions(),
                    kafkaConfigData.getReplicationFactor());
        }).collect(Collectors.toList());
        return adminClient.createTopics(kafkaTopics);
    }

    private Collection<TopicListing> getTopics() {
        Collection<TopicListing> topics;
        try {
            topics = retryTemplate.execute(this::doGetTopics);
        } catch (Throwable t) {
            throw new KafkaClientException("Reached max number of retry for reading kafka topics", t);
        }
        return topics;
    }

    private Collection<TopicListing> doGetTopics(RetryContext retryContext) throws ExecutionException, InterruptedException {
        LOG.info("Reading kafka topics {}, attempt {}",
                kafkaConfigData.getTopicNamesToCreate().toArray(), retryContext.getRetryCount());
        Collection<TopicListing> topics = adminClient.listTopics().listings().get();
        if (topics != null) {
            topics.forEach(topic -> LOG.debug("Topic with name {}", topic.name()));
        }
        return topics;
    }


}
