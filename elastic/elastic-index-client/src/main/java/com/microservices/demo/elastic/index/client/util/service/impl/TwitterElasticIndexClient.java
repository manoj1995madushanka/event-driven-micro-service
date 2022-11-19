package com.microservices.demo.elastic.index.client.util.service.impl;

import com.microservices.demo.config.ElasticConfigData;
import com.microservices.demo.elastic.index.client.util.ElasticIndexUtil;
import com.microservices.demo.elastic.index.client.util.service.ElasticIndexClient;
import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwitterElasticIndexClient implements ElasticIndexClient<TwitterIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticIndexClient.class);

    private final ElasticConfigData elasticConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticIndexUtil elasticIndexUtil;

    public TwitterElasticIndexClient(ElasticConfigData elasticConfigData, ElasticsearchOperations elasticsearchOperations, ElasticIndexUtil elasticIndexUtil) {
        this.elasticConfigData = elasticConfigData;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticIndexUtil = elasticIndexUtil;
    }

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<IndexQuery> indexQueries = elasticIndexUtil.getIndexQueries(documents);
        List<String> documentIds = elasticsearchOperations.bulkIndex(
                indexQueries, IndexCoordinates.of(elasticConfigData.getIndexName())
        );
        LOG.info("Documents indexed successfully with type: {} and ids: {}", TwitterIndexModel.class.getName(), documentIds);
        return documentIds;
    }
}
