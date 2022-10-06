package com.microservice.exception;

public class TwitterToKafkaServiceException extends RuntimeException {
    public TwitterToKafkaServiceException() {
        super();
    }

    public TwitterToKafkaServiceException(String message) {
        super(message);
    }

}
