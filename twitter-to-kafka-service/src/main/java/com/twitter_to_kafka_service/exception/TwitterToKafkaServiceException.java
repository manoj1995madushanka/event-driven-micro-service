package com.twitter_to_kafka_service.exception;

public class TwitterToKafkaServiceException extends RuntimeException {
    public TwitterToKafkaServiceException() {
        super();
    }

    public TwitterToKafkaServiceException(String message) {
        super(message);
    }

}
