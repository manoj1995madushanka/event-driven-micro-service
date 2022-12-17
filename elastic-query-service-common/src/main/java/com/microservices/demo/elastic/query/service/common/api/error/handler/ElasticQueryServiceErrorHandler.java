package com.microservices.demo.elastic.query.service.common.api.error.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * controllerAdvice annotation enables exception handling globally
 * */
@ControllerAdvice
public class ElasticQueryServiceErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticQueryServiceErrorHandler.class);

    /**
     * ExceptionHandler used for sets specific exception to handle
     *
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handle(AccessDeniedException exception){
        LOG.error("Access denied exception",exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to access this resource");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException exception){
        LOG.error("Illegal argument exception",exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Illegal argument exception");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException exception){
        LOG.error("Service runtime exception",exception);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Service runtime exception");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception exception){
        LOG.error("Server error occurred",exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException e) {
        LOG.error("Method argument validation exception!", e);
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error ->
                errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }



}
