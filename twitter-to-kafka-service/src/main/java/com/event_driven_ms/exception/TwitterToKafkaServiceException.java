package com.event_driven_ms.exception;

public class TwitterToKafkaServiceException extends RuntimeException {
    public TwitterToKafkaServiceException() {
        super();
    }

    public TwitterToKafkaServiceException(String message) {
        super(message);
    }

}
