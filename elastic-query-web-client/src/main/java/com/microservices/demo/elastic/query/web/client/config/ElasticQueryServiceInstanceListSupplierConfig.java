package com.microservices.demo.elastic.query.web.client.config;

import com.microservices.demo.config.ElasticQueryWebClientConfigData;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

/**
 * contains logic for front end load balancing
 * */
@Configuration
@Primary // make sure we want to this class and ignore other implemented class
public class ElasticQueryServiceInstanceListSupplierConfig implements ServiceInstanceListSupplier {

    private final ElasticQueryWebClientConfigData.WebClient webClientConfig;

    public ElasticQueryServiceInstanceListSupplierConfig(ElasticQueryWebClientConfigData.WebClient webClientConfig) {
        this.webClientConfig = webClientConfig;
    }

    @Override
    public String getServiceId() {
        return webClientConfig.getServiceId();
    }

    /**
     * Flux is a reactive stream that can emits 0 to n elements
     * */
    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(
                webClientConfig.getInstances().stream()
                        .map(instance ->
                                new DefaultServiceInstance(
                                        instance.getId(),
                                        getServiceId(),
                                        instance.getHost(),
                                        instance.getPort(),
                                        false
                                )).collect(Collectors.toList()));

    }
}
