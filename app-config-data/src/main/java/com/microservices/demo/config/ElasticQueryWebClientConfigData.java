package com.microservices.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-query-web-client")
public class ElasticQueryWebClientConfigData {

    private WebClient webClient;
    private Query queryByText;

    /**
     * we are using inner class because in config-client-elastic_query_web.yml
     * has WebClient object as separate node
     */
    @Data
    public static class WebClient {
        private Integer connectTimeoutMs;
        private Integer readTimeoutMs;
        private Integer writeTimeoutMs;
        private Integer maxInMemorySize;
        private String contentType;
        private String acceptType;
        private String baseUrl;
        private String serviceId;
        private List<Instance> instances;
    }

    @Data
    public static class Query{
        private String method;
        private String accept;
        private String uri;
    }

    @Data
    public static class Instance{
        public String id;
        public String host;
        public Integer port;
    }
}
